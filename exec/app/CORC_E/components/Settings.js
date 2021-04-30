import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

export default function Home() {
  return (
    // <View style={styles.container}>
    //   <View style={styles.contents}>
    //     <Text>여기가 환경설정이다</Text>
    //   </View>
    // </View>
    <View style={styles.container}>
      <View style={styles.center}>
        <View style={styles.behind}></View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  // container: {
  //   flex: 1,
  // },
  // contents: {
  //   flex: 1,
  //   justifyContent: 'center',
  //   alignItems: 'center',
  // },
  container: {
    flex: 1,
    alignItems: 'center',
    height: 100,
    justifyContent: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  center: {
    width: 200,
    height: 200,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.1)',
    borderRadius: 15,
  },
});
