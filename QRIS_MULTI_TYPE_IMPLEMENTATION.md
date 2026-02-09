# QRIS Multi-Type Support Implementation Guide

## Overview

Implementasi ini menambahkan dukungan untuk multiple tipe QRIS dalam satu endpoint `/qris-service/inquiries` dengan automatic type detection dan validasi yang sesuai untuk setiap tipe.

### Supported QRIS Types

1. **MPM Payment** - QRIS untuk pembayaran merchant (existing functionality)
2. **Transfer** - QRIS untuk transfer antar rekening (NEW)
3. **Tuntas** - QRIS untuk penarikan tunai di ATM (future implementation)

---

## Architecture

### Class Hierarchy

```
QrisPayload (abstract)
├── QrisMpmPaymentPayload (concrete)
├── QrisTransferPayload (concrete)
└── QrisTuntasPayload (future)
```

### Key Components

#### 1. `QrisType` (Enum)
Enum untuk mengidentifikasi tipe QRIS:
- `MPM_PAYMENT` - Pembayaran merchant
- `TRANSFER` - Transfer/Tuntas
- `TUNTAS` - Penarikan tunai (future)

**Location:** `com.astrapay.qris.mpm.object.QrisType`

#### 2. `QrisPayload` (Abstract Base Class)
Base class dengan common validations untuk semua tipe QRIS:
- ✅ CheckSum validation (CRC)
- ✅ Payload Format Indicator position & value
- ✅ CRC position validation
- ✅ Basic character length validations

**Location:** `com.astrapay.qris.mpm.object.QrisPayload`

**Common Fields:**
- `payload` - Raw QR text (max 512 chars)
- `qrisRoot` - Parsed data objects map

**Abstract Method:**
```java
public abstract QrisType getQrisType();
```

#### 3. `QrisMpmPaymentPayload` (Concrete)
MPM Payment specific implementation dengan validations:
- ✅ Merchant Category Code (ID 52) - mandatory
- ✅ Merchant Account Information (ID 26-45 atau 51) - minimal 1
- ✅ Transaction Currency (ID 53) - mandatory
- ✅ Merchant Name & City (ID 59, 60) - mandatory
- ✅ Transaction Amount, Tip validations
- ✅ Country Code & Postal Code validations

**Location:** `com.astrapay.qris.mpm.object.QrisMpmPaymentPayload`

#### 4. `QrisTransferPayload` (Concrete)
Transfer specific implementation dengan validations:
- ✅ Transfer Account Information (ID 40) - mandatory
  - Sub-tag 00: Reverse Domain (mandatory)
  - Sub-tag 01: Customer PAN 16-19 digits (mandatory)
  - Sub-tag 02: Beneficiary ID (mandatory)
  - Sub-tag 04: Bank Identifier Code (optional)
- ✅ Purpose of Transaction (tag 62->08) - mandatory
  - Must contain: BOOK, DMCT, or XBCT
- ✅ Transaction Currency (ID 53) - mandatory
- ✅ Beneficiary Name & City (ID 59, 60) - mandatory
- ✅ Additional Data (ID 62) - mandatory
- ❌ NO Merchant Category Code (ID 52)

**Location:** `com.astrapay.qris.mpm.object.QrisTransferPayload`

---

## Type Detection Logic

### Algorithm

Parser menggunakan logic berikut untuk mendeteksi tipe QRIS:

```
1. Parse QR text untuk mendapatkan tag 62 (Additional Data)
2. Jika tag 62 ada:
   a. Parse sub-tags dari tag 62
   b. Jika tag 08 (Purpose of Transaction) ditemukan:
      - Jika value berisi "BOOK" → TRANSFER
      - Jika value berisi "DMCT" → TRANSFER
      - Jika value berisi "XBCT" → TRANSFER
3. Selain kondisi di atas → MPM_PAYMENT (default)
```

### Implementation

**Location:** `com.astrapay.qris.mpm.QrisParser.detectQrisType()`

```java
private QrisType detectQrisType(String qris) {
    // Parse root to get tag 62
    Map<Integer, QrisDataObject> tempMap = new LinkedHashMap<>();
    parseRoot(qris, tempMap);
    
    // Check Additional Data (ID 62)
    if (tempMap.containsKey(62)) {
        QrisDataObject additionalData = tempMap.get(62);
        Map<Integer, QrisDataObject> tag62Map = new LinkedHashMap<>();
        parser(additionalData.getValue(), tag62Map);
        
        // Check Purpose of Transaction (tag 08)
        if (tag62Map.containsKey(8)) {
            String purpose = tag62Map.get(8).getValue();
            if (purpose.contains("BOOK") || 
                purpose.contains("DMCT") || 
                purpose.contains("XBCT")) {
                return QrisType.TRANSFER;
            }
        }
    }
    
    return QrisType.MPM_PAYMENT; // Default
}
```

---

## QR Text Format Comparison

### MPM Payment Example

```
00020101021126...52044829530336054...5802ID5907ANTONIO6007JAKARTA61051031062386304CCEF
```

**Breakdown:**
- `0002|01` - Payload Format Indicator
- `0102|12` - Point of Initiation (dynamic)
- `26...` - Merchant Account Information
- `5204|4829` - **Merchant Category Code** ✅
- `5303|360` - Transaction Currency
- `5802|ID` - Country Code
- `5907|ANTONIO` - Merchant Name
- `6007|JAKARTA` - Merchant City
- `6238` - Additional Data (optional)
- `6304|CCEF` - CRC

### Transfer Example

```
00020101024064...0118936023451234567890...5303360540850000.005802ID5907ANTONIO6007JAKARTA61051031062380804DMCT6304CCEF
```

**Breakdown:**
- `0002|01` - Payload Format Indicator
- `0102|12` - Point of Initiation (dynamic)
- `4064|...` - **Transfer Account Information** ✅
  - `00|19|ID.CO.PJSPNAME1.WWW` - Reverse Domain
  - `01|18|936023451234567890` - Customer PAN
  - `02|15|KLMNO1234512345` - Beneficiary ID
- ❌ NO Merchant Category Code (ID 52)
- `5303|360` - Transaction Currency
- `5408|50000.00` - Transaction Amount
- `5802|ID` - Country Code
- `5907|ANTONIO` - Beneficiary Name
- `6007|JAKARTA` - Beneficiary City
- `6238` - **Additional Data** ✅ (mandatory for Transfer)
  - `08|04|DMCT` - **Purpose of Transaction** ✅
- `6304|CCEF` - CRC

---

## Custom Validation Annotations

### 1. `@TransferAccountInformationValid`

Memvalidasi struktur Transfer Account Information (ID 40).

**Validations:**
- ✅ Tag 40 exists
- ✅ Sub-tag 00 (Reverse Domain) exists and not empty
- ✅ Sub-tag 01 (Customer PAN) exists, numeric, 16-19 digits
- ✅ Sub-tag 02 (Beneficiary ID) exists, not empty, max 15 chars
- ✅ Sub-tag 04 (BIC) if exists, must be 8-11 chars

**Location:** `com.astrapay.qris.mpm.validation.constraints.TransferAccountInformationValid`

**Validator:** `com.astrapay.qris.mpm.validation.TransferAccountInformationValidator`

**Usage:**
```java
@TransferAccountInformationValid
public class QrisTransferPayload extends QrisPayload {
    // ...
}
```

### 2. `@PurposeOfTransactionValid`

Memvalidasi Purpose of Transaction untuk Transfer QRIS.

**Validations:**
- ✅ Tag 62 (Additional Data) exists
- ✅ Sub-tag 08 (Purpose) exists
- ✅ Purpose value contains: BOOK, DMCT, or XBCT

**Location:** `com.astrapay.qris.mpm.validation.constraints.PurposeOfTransactionValid`

**Validator:** `com.astrapay.qris.mpm.validation.PurposeOfTransactionValidator`

**Usage:**
```java
@PurposeOfTransactionValid
public class QrisTransferPayload extends QrisPayload {
    // ...
}
```

---

## API Endpoint

### Endpoint Specification

**URL:** `POST /qris-service/inquiries`

**Content-Type:** `application/qris`

**Accept:** `application/json` atau `application/xml`

### Controller Method (No Changes Required)

```java
@PostMapping(
    value = "/inquiries", 
    consumes = "application/qris", 
    produces = {"application/json", "application/xml"}
)
@SDKTokenIsValid
@UserIsNotFraud
@LogExecutionTime
@LogRequestResponse
public ResponseEntity<QrInquiryDto> parsePost(@RequestBody @Valid QrisPayload qrisPayload) 
        throws NotFoundException, IOException, CheckDigitException {
    
    QrInquiryDto response = qrService.translateQrisReal(qrisPayload, TOKEN_EXPIRED_MINUTE);
    
    if (fundProperties.isInquirySwitcherEnabled()) {
        QrInquiryDto inquiryFundResponse = qrService.fundInquiries(response);
        return new ResponseEntity<>(inquiryFundResponse, HttpStatus.OK);
    }
    
    return new ResponseEntity<>(response, HttpStatus.OK);
}
```

**Key Points:**
- ✅ Endpoint signature **TIDAK BERUBAH**
- ✅ Masih menerima `@RequestBody @Valid QrisPayload`
- ✅ Type detection dilakukan oleh `QrisParser` sebelum masuk ke controller
- ✅ Validation otomatis di-trigger sesuai concrete type (polymorphism)

---

## Parsing Flow

### High-Level Flow

```
1. Client sends QR text string to endpoint
2. Parser receives raw QR text
3. Type Detection:
   ├─ Parse tag 62 → tag 08
   ├─ If BOOK/DMCT/XBCT → Create QrisTransferPayload
   └─ Else → Create QrisMpmPaymentPayload
4. Parse QR text into data objects
5. Validation triggered automatically (based on concrete type)
6. Return parsed & validated payload to controller
7. Service layer processes based on payload.getQrisType()
```

### Detailed Parsing Steps

#### Step 1: Type Detection
```java
QrisType type = detectQrisType(qrText);
// Returns: TRANSFER or MPM_PAYMENT
```

#### Step 2: Create Instance
```java
QrisPayload payload = createPayloadByType(type);
// Creates: QrisTransferPayload or QrisMpmPaymentPayload
```

#### Step 3: Parse Root Objects
```java
Map<Integer, QrisDataObject> qrisRoot = parseRoot(qrText);
// Parses: All ID-Length-Value data objects
```

#### Step 4: Parse Templates
```java
// MPM Payment specific
parseMerchantAccountInformationTemplate(qrisRoot);  // ID 26-45
parseMerchantDomesticRepository(qrisRoot);          // ID 51

// Transfer specific
parseTransferAccountInformation(qrisRoot);          // ID 40

// Common
parseAdditionalDataFieldTemplate(qrisRoot);         // ID 62
parseMerchantInformationLanguageTemplate(qrisRoot); // ID 64
```

#### Step 5: Validation
```java
// Automatic validation based on concrete type annotations
// QrisMpmPaymentPayload: checks MCC, Merchant Account, etc.
// QrisTransferPayload: checks Transfer Account, Purpose, etc.
```

---

## Data Mapping to ISO8583

### Transfer QRIS Mapping

| QRIS Field | Tag | ISO8583 Field | Description |
|------------|-----|---------------|-------------|
| Customer PAN | 40→01 | DE 02 | Primary Account Number (16-19 digits) |
| Beneficiary ID | 40→02 | DE 103 | Account Identification |
| Bank Identifier Code | 40→04 | DE 48 Tag BC | BIC/SWIFT Code (optional) |
| Merchant Category Code | 52 | DE 18 | N/A for Transfer |
| Transaction Currency | 53 | DE 49 | Currency Code (360 = IDR) |
| Transaction Amount | 54 | DE 04 | Transaction Amount |
| Country Code | 58 | DE 43 pos 39-40 | Country Code (ID) |
| Beneficiary Name | 59 | DE 43 pos 1-25 | Merchant/Beneficiary Name |
| Beneficiary City | 60 | DE 43 pos 26-38 | City |
| Postal Code | 61 | DE 57 | Postal Code |
| Purpose of Transaction | 62→08 | DE 57-tag 08 | BOOK/DMCT/XBCT |
| Unique per Generated | 62→99 | DE 57-tag 99 | Unique ID |

---

## Usage Examples

### Example 1: Parse MPM Payment QR

```java
String mpmQrText = "00020101021126...52044829...6304CCEF";

QrisParser parser = new QrisParser();
QrisPayload payload = parser.parse(mpmQrText);

// payload instanceof QrisMpmPaymentPayload → true
// payload.getQrisType() → QrisType.MPM_PAYMENT

Map<Integer, QrisDataObject> root = payload.getQrisRoot();
String mcc = root.get(52).getValue(); // "4829"
```

### Example 2: Parse Transfer QR

```java
String transferQrText = "00020101024064...62380804DMCT...6304CCEF";

QrisParser parser = new QrisParser();
QrisPayload payload = parser.parse(transferQrText);

// payload instanceof QrisTransferPayload → true
// payload.getQrisType() → QrisType.TRANSFER

Map<Integer, QrisDataObject> root = payload.getQrisRoot();
QrisDataObject transferAccount = root.get(40);
String customerPan = transferAccount.getTemplateMap().get(1).getValue();

QrisDataObject additionalData = root.get(62);
String purpose = additionalData.getTemplateMap().get(8).getValue(); // "DMCT"
```

### Example 3: Type-Based Processing in Service

```java
public QrInquiryDto translateQrisReal(QrisPayload payload, int timeout) {
    switch (payload.getQrisType()) {
        case MPM_PAYMENT:
            return processMpmPayment((QrisMpmPaymentPayload) payload);
        
        case TRANSFER:
            return processTransfer((QrisTransferPayload) payload);
        
        case TUNTAS:
            throw new UnsupportedOperationException("Tuntas not yet implemented");
        
        default:
            throw new IllegalStateException("Unknown QRIS type");
    }
}
```

---

## Migration Guide

### No Breaking Changes

✅ **Existing code tetap jalan** tanpa perubahan karena:
1. Endpoint signature tidak berubah
2. Default behavior adalah MPM_PAYMENT
3. Semua existing QR text MPM Payment akan ter-detect sebagai `MPM_PAYMENT`

### Required Service Layer Updates

Jika service layer perlu membedakan processing:

```java
// Before (masih berfungsi)
public QrInquiryDto translateQrisReal(QrisPayload payload, int timeout) {
    // Process as MPM Payment
}

// After (recommended untuk support Transfer)
public QrInquiryDto translateQrisReal(QrisPayload payload, int timeout) {
    if (payload.getQrisType() == QrisType.TRANSFER) {
        return processTransferQris((QrisTransferPayload) payload);
    }
    
    // Default MPM Payment processing
    return processMpmPaymentQris((QrisMpmPaymentPayload) payload);
}
```

---

## Testing

### Test Case 1: MPM Payment Detection

**Input:**
```
00020101021126580019ID.CO.PJSPNAME1.WWW010021234567890123456789026830150715ASTRAPAY2100176520448295303360540850000.005802ID5907ANTONIO6007JAKARTA610510310623086304CCEF
```

**Expected:**
- `payload.getQrisType()` → `MPM_PAYMENT`
- `payload instanceof QrisMpmPaymentPayload` → `true`
- `payload.getQrisRoot().get(52)` → exists (MCC)
- Validation passes for MPM Payment rules

### Test Case 2: Transfer Detection (BOOK)

**Input:**
```
00020101024064001019ID.CO.PJSPNAME1.WW010118936023451234567890021KLMNO12345123455303360540850000.005802ID5907ANTONIO6007JAKARTA61051031062380804BOOK99049926630475D5
```

**Expected:**
- `payload.getQrisType()` → `TRANSFER`
- `payload instanceof QrisTransferPayload` → `true`
- `payload.getQrisRoot().get(40)` → exists (Transfer Account)
- `payload.getQrisRoot().get(52)` → null (no MCC for Transfer)
- Purpose of Transaction → "BOOK"
- Validation passes for Transfer rules

### Test Case 3: Transfer Detection (DMCT)

**Input:**
```
00020101024064001019ID.CO.PJSPNAME1.WW010118936023451234567890021KLMNO12345123455303360540850000.005802ID5907ANTONIO6007JAKARTA61051031062380804DMCT99041234630475D5
```

**Expected:**
- `payload.getQrisType()` → `TRANSFER`
- Purpose of Transaction → "DMCT"

### Test Case 4: Validation Failure - Missing Customer PAN

**Input:** Transfer QR without Customer PAN (tag 40→01)

**Expected:**
- Validation fails
- Error message: "Transfer Account Information: Customer PAN (tag 01) is mandatory"

### Test Case 5: Validation Failure - Invalid Purpose

**Input:** Transfer QR dengan purpose value invalid

**Expected:**
- Validation fails
- Error message: "Purpose of Transaction (tag 62->08) has invalid value"

---

## Future Extensions

### Adding Tuntas Support

1. Create `QrisTuntasPayload` extends `QrisPayload`
2. Add Tuntas-specific validations
3. Update `detectQrisType()` dengan detection logic untuk Tuntas
4. Update `createPayloadByType()` untuk create `QrisTuntasPayload`

### Example:

```java
// QrisTuntasPayload.java
@MandatoryField(id = XX)  // Tuntas-specific fields
@TuntasSpecificValidation
public class QrisTuntasPayload extends QrisPayload {
    @Override
    public QrisType getQrisType() {
        return QrisType.TUNTAS;
    }
}

// QrisParser.java
private QrisType detectQrisType(String qris) {
    // ... existing Transfer detection ...
    
    // Add Tuntas detection logic
    if (qrisMap.containsKey(YY)) {  // Tuntas identifier
        return QrisType.TUNTAS;
    }
    
    return QrisType.MPM_PAYMENT;
}
```

---

## Summary

### ✅ Benefits

1. **No Breaking Changes** - Existing code tetap berfungsi
2. **Type Safety** - Compile-time checking dengan polymorphism
3. **Clean Separation** - Validation terpisah per tipe
4. **Extensible** - Mudah tambah tipe baru (Tuntas)
5. **Maintainable** - Clear structure & documentation
6. **Automatic** - Type detection & validation otomatis

### 📋 Checklist Implementation

- [x] Create `QrisType` enum
- [x] Refactor `QrisPayload` to abstract base class
- [x] Create `QrisMpmPaymentPayload` concrete class
- [x] Create `QrisTransferPayload` concrete class
- [x] Create `@TransferAccountInformationValid` annotation
- [x] Create `@PurposeOfTransactionValid` annotation
- [x] Implement `TransferAccountInformationValidator`
- [x] Implement `PurposeOfTransactionValidator`
- [x] Update `QrisParser` with type detection
- [x] Add comprehensive documentation

### 🚀 Next Steps

1. ✅ **Implementation Complete** - All classes & validators created
2. ⏳ **Testing** - Create unit tests for all scenarios
3. ⏳ **Integration Testing** - Test dengan real QR text samples
4. ⏳ **Service Layer Update** - Update `QrService` untuk handle Transfer
5. ⏳ **Deployment** - Deploy ke environment

---

**Version:** 1.0  
**Last Updated:** February 9, 2026  
**Author:** Arthur Purnama
