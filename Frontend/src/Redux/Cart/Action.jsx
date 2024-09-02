/* istanbul ignore file */ 

import {
    FETCH_CART_SUCCESS,
    ADD_TO_CART_SUCCESS,
    UPDATE_CART_ITEM_SUCCESS,
    DELETE_CART_ITEM_SUCCESS,
    SET_NOTIFICATION
} from './actionTypes';
 
export const fetchCartSuccess = (cartItems, totalItems, totalPrice, totalSalePrice) => ({
    type: FETCH_CART_SUCCESS,
    payload: { cartItems, totalItems, totalPrice, totalSalePrice }
});
 
export const addToCartSuccess = (notification) => ({
    type: ADD_TO_CART_SUCCESS,
    payload: notification
});
 
export const updateCartItemSuccess = () => ({
    type: UPDATE_CART_ITEM_SUCCESS
});
 
export const deleteCartItemSuccess = () => ({
    type: DELETE_CART_ITEM_SUCCESS
});