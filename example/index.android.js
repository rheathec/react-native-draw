/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {AppRegistry, requireNativeComponent, StyleSheet, View} from 'react-native';

const RCTPainter = requireNativeComponent('RCTPainter', null);

export default class example extends Component {

    render() {
        return (
            <View style={styles.container}>
                <RCTPainter style={[styles.container]}/>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexBasis: 1,
        width: 200,
        height: 200,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#89ff76',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});

AppRegistry.registerComponent('example', () => example);
