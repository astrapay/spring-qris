package com.astrapay.qris;

import com.astrapay.qris.object.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class QrisMapperTest {

  @InjectMocks
  private QrisMapper qrisMapper;

  @Test
  void mapToStringQrisWithTipPercentageTest() {
    String payloadString  = "00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE520458125303360540650000055020357030.75802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO6304265F";
    QrisParser qrisParser = new QrisParser();
    QrisPayload qrisPayload = qrisParser.parse(payloadString);
    Qris qris = qrisMapper.map(qrisPayload.getQrisRoot());
    String value  = qrisMapper.mapToString(qris);
    assertEquals(payloadString,value);
  }


  @Test
  void mapToStringQrisWithTipFixedTest() {
    String payloadString  = "00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE520458125303360540650000055020357030.75802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO6304265F";
    QrisParser qrisParser = new QrisParser();

    QrisPayload qrisPayload = qrisParser.parse(payloadString);
    Qris qris = qrisMapper.map(qrisPayload.getQrisRoot());
    String value  = qrisMapper.mapToString(qris);
    assertEquals(payloadString,value);
  }


  @Test
  void mapToStringQrisWithoutTipTest() {
    String payloadString  = "00020101021226580006id.ovo01189360091200006049620215yq38ZDs7MI6oaz50303UKE51450015ID.OR.GPNQR.WWW0215ID10200416988350303UKE52045812530336054065000005802ID5911Tims Donuts6013Jakarta Barat61051161062310507VCcCtqJ0716HgxK16eFVHfvKnGO63048A85";
    QrisParser qrisParser = new QrisParser();
    QrisPayload qrisPayload = qrisParser.parse(payloadString);
    Qris qris = qrisMapper.map(qrisPayload.getQrisRoot());
    String value  = qrisMapper.mapToString(qris);
    assertEquals(payloadString,value);
  }

  @Test
  void mapToStringQrisWithoutAdditionalDataTest() {
    String payloadString  = "00020101021126570011ID.DANA.WWW011893600915300060605702090006060570303UMI51440014ID.CO.QRIS.WWW0215ID10190046990790303UMI5204481453033605802ID5912Saifullah.id6014Kota Pontianak61057812463045F72";
    QrisParser qrisParser = new QrisParser();
    QrisPayload qrisPayload = qrisParser.parse(payloadString);
    Qris qris = qrisMapper.map(qrisPayload.getQrisRoot());
    String value  = qrisMapper.mapToString(qris);
    assertEquals(payloadString,value);
  }
}