import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, Platform, ScrollView } from 'react-native';
import Constants from 'expo-constants';
import { useSelector } from 'react-redux';
import Card from '../components/Card';
import axios from 'axios';
import Payment from '../components/Payment';
import PaymentHistoryIcon from '../components/icons/PaymentHistoryIcon';

export default function Wallet() {
  const userId = useSelector((state) => state.auth.userId);
  const [isLoading, setIsLoading] = useState(true);
  const [userInfo, setUserInfo] = useState();
  const [payment, setPayment] = useState();

  const SERVER_URL = 'http://192.168.219.102:8765/shinhan/';

  function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  }

  useEffect(() => {
    (async () => {
      let response = await axios.get(
        SERVER_URL + 'admin/user/info?userId=' + userId
      );
      setUserInfo(response.data);

      let response2 = await axios.get(
        SERVER_URL + 'user/payment?userId=' + userId
      );
      setPayment(response2.data);
      // console.log('paymentList ===> : ', response2.data);
      setIsLoading(false);
    })();
  }, []);
  if (isLoading) {
    return <></>;
  }
  return (
    <View style={styles.container}>
      <View style={styles.contents}>
        <View
          style={{
            flex: 1,
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          <Text style={{ fontSize: 35, color: '#414251', marginRight: 10 }}>
            {numberWithCommas(userInfo.info.balance)}
          </Text>
          <Text
            style={{
              fontSize: 24,
              color: '#414251',
            }}
          >
            원
          </Text>
        </View>

        <View
          style={{
            alignItems: 'flex-end',
            paddingRight: 25,
            paddingBottom: 10,
          }}
        >
          <Text style={{ color: '#414251' }}>
            / {numberWithCommas(userInfo.info.cardLimit)} 원
          </Text>
        </View>
        <View
          style={{
            // flex: 1,
            flexDirection: 'row',
            alignItems: 'center',
            paddingTop: 10,
            paddingLeft: 20,
          }}
        >
          <Text style={{ color: 'gray', fontSize: 13, marginLeft: 6 }}>
            남은 한도 / 총 한도
          </Text>
        </View>
      </View>

      <View
        style={{
          flex: 0.7,
          flexDirection: 'row',
          alignItems: 'flex-end',
          // justifyContent: 'flex-start',
          // borderBottomColor: '#737373',
          paddingBottom: '3%',
        }}
      >
        <PaymentHistoryIcon color={'#414251'} size="30" />
        <Text style={{ fontSize: 24, marginLeft: 10, color: '#414251' }}>
          이용 내역
        </Text>
      </View>
      <Card
        style={{
          // marginHorizontal: 10,
          // marginTop: 10,
          marginBottom: '10%',
          flex: 3.5,
        }}
      >
        <ScrollView
          style={{
            flex: 1,
            marginHorizontal: 20,
            marginTop: 10,
          }}
        >
          <View style={{ flex: 1, alignItems: 'stretch' }}>
            {payment.paymentList.content.map((payment) => (
              <Payment
                key={payment.paymentId}
                date={payment.date}
                store={payment.store}
                total={payment.total}
                categoryCode={payment.store.category.categoryCode}
              />
            ))}
          </View>
        </ScrollView>
      </Card>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: Platform.OS === `ios` ? 0 : Constants.statusBarHeight,
    paddingHorizontal: '10%',
  },
  contents: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
