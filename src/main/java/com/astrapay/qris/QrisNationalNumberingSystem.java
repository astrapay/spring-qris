package com.astrapay.qris;

public interface QrisNationalNumberingSystem {

    enum Switching{
        ALTO, ARTAJASA, RINTIS, JALIN
    }

    enum Pjsp{
        SINARMAS(93600153, Switching.ALTO),
        MAYBANK(93600016, Switching.ALTO),
        SHOPEE(93600918, Switching.ALTO),
        BLUEPAY(93600919, Switching.ALTO),
        DOKU(93600899, Switching.ALTO),
        ASTRAPAY(93600822, Switching.ALTO),
        BANK_TABUNGAN_NEGARA(93600200,Switching.ALTO),
        DIGITAL_BCA(93600501,Switching.ALTO),
        TRUEMONEY(93600828,Switching.ALTO),
        DIPAY(93600826,Switching.ALTO),
        BANK_GANESHA(93600161,Switching.ALTO),
        BANK_BPD_KALIMANTAN_TIMUR_KALIMANTAN_UTARA(93600124,Switching.ALTO),
        MIDAZPAY(93600821, Switching.ALTO),
        BANK_NEO_COMMERCE(93600490, Switching.ALTO),
        PT_BANK_PANIN(93600019, Switching.ALTO),
        BANK_JAGO(93600542, Switching.ALTO),
        SEABANK(93600535, Switching.ALTO),
        CASHLEZ(93600839, Switching.ALTO),
        HONEST(93600847, Switching.ALTO),
        FINPAY(93600777, Switching.ALTO),
        BANK_CAPITAL_INDONESIA(93600054, Switching.ALTO),
        PT_BPD_SUMATERA_UTARA(93600117, Switching.ALTO),
        PT_NUSAPAY_SOLUSI_INDONESIA(93600836, Switching.ALTO),
        PAYLABS(93600849, Switching.ALTO),
        XENDIT(93600848, Switching.ALTO),
        ZIPAY(93600825, Switching.ALTO),
        NETZME_ALTO(93600814, Switching.ALTO),
        MNC_SPIN_ALTO(93600816, Switching.ALTO),
        GUDANG_VOUCHER_ALTO(93600916, Switching.ALTO),
        PT_SUPER_BANK_INDONESIA(93600562,Switching.ALTO),
        PT_BANK_NANO_SYARIAH(93600253,Switching.ALTO),

        BANK_JASA_JAKARTA(93600472, Switching.ALTO),

        BNI(93600009, Switching.ARTAJASA),
        PERMATA(93600013, Switching.ARTAJASA),
        BPD_JATIM(93600114, Switching.ARTAJASA),
        BPD_BALI(93600129, Switching.ARTAJASA),
        BANK_SYARIAH_MANDIRI(93600451, Switching.ARTAJASA),
        NOBU(93600503, Switching.ARTAJASA),
        TSEL_LINK_AJA(93600911, Switching.ARTAJASA),
        OVO(93600912, Switching.ARTAJASA),
        GOPAY(93600914, Switching.ARTAJASA),
        DANA(93600915, Switching.ARTAJASA),
        PAYTREN(93600917, Switching.ARTAJASA),
        UOB(93600023, Switching.ARTAJASA),
        BPD_NTT(93600130, Switching.ARTAJASA),
        GUDANG_VOUCHER(93600916, Switching.ARTAJASA),
        BANK_MUAMALAT(93600147, Switching.ARTAJASA),
        BANK_BPD_JAMBI(93600115, Switching.ARTAJASA),
        BANK_QNB(93600167, Switching.ARTAJASA),
        BANK_MAYORA(93600553, Switching.ARTAJASA),
        BPD_PAPUA(93600132,Switching.ARTAJASA),
        BANK_KALBAR(93600123,Switching.ARTAJASA),
        BANK_JABAR_BANTEN_SYARIAH(93600425,Switching.ARTAJASA),
        BANK_SHINHAN(93600152, Switching.ARTAJASA),
        MOTION_BANKING(93600485, Switching.ARTAJASA),
        PAC_CASH(93600820, Switching.ARTAJASA),
        BANK_LAMPUNG(93600121, Switching.ARTAJASA),
        BANK_KALSEL(93600122, Switching.ARTAJASA),
        CITIBANK(93600031,  Switching.ARTAJASA),
        BANK_ALADIN_SYARIAH(93600947, Switching.ARTAJASA),
        M_BAYAR(93600829,  Switching.ARTAJASA),
        BANK_SULUTGO(93600127, Switching.ARTAJASA),
        BANK_WOORI_SAUDARA(93600212, Switching.ARTAJASA),
        BANK_PEMBANGUNAN_DAERAH_BENGKULU( 93600133, Switching.ARTAJASA),
        CASHFAZZ( 93600837, Switching.ARTAJASA),

        SINARMAS_RINTIS(93600153,Switching.RINTIS),
        KB_BUKOPIN(93600441,Switching.RINTIS),
        BPD_JATIM_RINTIS(93600114,Switching.RINTIS),
        DANAMON(93600011, Switching.RINTIS),
        BANK_RIAU_KEPRI(93600119,Switching.RINTIS),
        BCA(93600014, Switching.RINTIS),
        BCA_SYARIAH(93600536, Switching.RINTIS),
        CIMB(93600022, Switching.RINTIS),
        OCBC(93600028, Switching.RINTIS),
        BANK_JABAR(93600110, Switching.RINTIS),
        BANK_DKI(93600111, Switching.RINTIS),
        NAGARI(93600118, Switching.RINTIS),
        BANK_MEGA(93600426, Switching.RINTIS),
        OTTOCASH(93600811, Switching.RINTIS),
        BPD_SUMSEL_BABEL(93600120, Switching.RINTIS),
        MULTIARTA_SENTOSA(93600548,Switching.RINTIS),
        SULSELBAR(93600126, Switching.RINTIS),
        BTPN(93600213, Switching.RINTIS),
        HANA(93600484, Switching.RINTIS),
        KASPRO(93600812, Switching.RINTIS),
        ISAKU(93600920, Switching.RINTIS),
        BPD_DIY(93600112, Switching.RINTIS),
        BANK_SAHABAT_SAMPOERNA(93600523, Switching.RINTIS),
        BANK_ARTHA_GRAHA_INTERNATIONAL(93600037, Switching.RINTIS),
        BUMI_ARTA(93600076,Switching.RINTIS),
        BANK_INA_PERDANA(93600513, Switching.RINTIS),
        BPD_KALIMANTAN_BARAT(93600123, Switching.RINTIS),
        QRIS_DSP(93600998, Switching.RINTIS),
        AMAR_BANK(93600531, Switching.RINTIS),
        BANK_MASPION(93600157, Switching.RINTIS),
        BANK_CCBI(93600036, Switching.RINTIS),
        BLUEPAY_RINTIS(93600919, Switching.RINTIS),
        YOURPAY(93600923, Switching.RINTIS),
        PT_BANK_MESTIKA_DHARMA(93600151, Switching.RINTIS),


        BANK_ACEH_SYARIAH(93600116,Switching.JALIN),
        BPD_NTT_JALIN(93600130,Switching.JALIN),
        BRI(93600002, Switching.JALIN),
        MANDIRI(93600008, Switching.JALIN),
        BIMASAKTI(93600815, Switching.JALIN),
        TELKOM(93600898, Switching.JALIN),
        BRISPAY(93600422, Switching.JALIN),
        FINNET(93600777, Switching.JALIN),
        NETZME(93600814, Switching.JALIN),
        MNC_SPIN(93600816, Switching.JALIN),
        PAYDIA(93600818, Switching.JALIN),
        YUKK(93600817, Switching.JALIN),
        BAYARIND(93600808,Switching.JALIN),
        POS_INDONESIA(93608161,Switching.JALIN),
        GPAY(93600813,Switching.JALIN),
        BANK_RAYA(93600494, Switching.JALIN),
        YODU(93600830, Switching.JALIN),
        GDC_PAY(93600832, Switching.JALIN),
        BATPAY(93600841, Switching.JALIN),
        PT_BANK_PANIN_DUBAI_SYARIAH(93600517, Switching.JALIN),
        MANDIRI_TASPEN(93600564, Switching.JALIN);

        private Integer code;
        private Switching switching;

        Pjsp(Integer code, Switching switching) {
            this.code = code;
            this.switching = switching;
        }

        public Integer getCode() {
            return code;
        }

        public Switching getSwitching() {
            return switching;
        }
        public static QrisNationalNumberingSystem.Pjsp valueOf(Integer code) throws IllegalArgumentException {
            QrisNationalNumberingSystem.Pjsp pjspReturn = null;
            for (QrisNationalNumberingSystem.Pjsp pjsp : values())
                if (pjsp.getCode().equals(code)) {
                    pjspReturn = pjsp;
                    break;
                }
            if (pjspReturn == null) throw new IllegalArgumentException("Invalid MCC " + code);
            return pjspReturn;
        }
    }
}
