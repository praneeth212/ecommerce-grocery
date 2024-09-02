import axios from 'axios';
 
export const SET_CURRENT_STEP = 'SET_CURRENT_STEP';
export const SET_SELECTED_ADDRESS = 'SET_SELECTED_ADDRESS';
export const SET_ORDER_DATA = 'SET_ORDER_DATA';
export const SET_PAYMENT_METHOD = 'SET_PAYMENT_METHOD';
export const SET_DEFAULT_ADDRESS = 'SET_DEFAULT_ADDRESS';
export const SET_USE_DEFAULT_ADDRESS = 'SET_USE_DEFAULT_ADDRESS';
export const SET_IS_ENTERING_ADDRESS_MANUALLY = 'SET_IS_ENTERING_ADDRESS_MANUALLY';
export const SET_CART = 'SET_CART';
export const SET_TOTAL_COST = 'SET_TOTAL_COST';
export const SET_SELECTED_DATE = 'SET_SELECTED_DATE';
export const SET_ADDRESS_FIELDS = 'SET_ADDRESS_FIELDS';
 
export const FETCH_ORDER_HISTORY_REQUEST = 'FETCH_ORDER_HISTORY_REQUEST';
export const FETCH_ORDER_HISTORY_SUCCESS = 'FETCH_ORDER_HISTORY_SUCCESS';
export const FETCH_ORDER_HISTORY_FAILURE = 'FETCH_ORDER_HISTORY_FAILURE';
 
export const FETCH_DELIVERED_HISTORY_REQUEST = 'FETCH_DELIVERED_HISTORY_REQUEST';
export const FETCH_DELIVERED_HISTORY_SUCCESS = 'FETCH_DELIVERED_HISTORY_SUCCESS';
export const FETCH_DELIVERED_HISTORY_FAILURE = 'FETCH_DELIVERED_HISTORY_FAILURE';
 
export const PLACE_ORDER_REQUEST = 'PLACE_ORDER_REQUEST';
export const PLACE_ORDER_SUCCESS = 'PLACE_ORDER_SUCCESS';
export const PLACE_ORDER_FAILURE = 'PLACE_ORDER_FAILURE';
 
export const fetchDefaultAddress = (userId) => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8085/api/users/${userId}/default`);
 
    dispatch({ type: SET_DEFAULT_ADDRESS, payload: response.data });
    dispatch({ type: SET_SELECTED_ADDRESS, payload: response.data.defaultAddress });
  } catch (error) {
    console.error('Error fetching default address:', error);
  }
};
 
export const fetchCart = () => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8084/cart/list`);
    dispatch({ type: SET_CART, payload: response.data });
 
  } catch (error) {
    console.error('Error fetching cart:', error);
  }
};
 
export const fetchOrderHistoryRequest = () => ({
  type: FETCH_ORDER_HISTORY_REQUEST,
});
 
export const fetchOrderHistorySuccess = (orderHistory) => ({
  type: FETCH_ORDER_HISTORY_SUCCESS,
  payload: orderHistory,
});
 
export const fetchOrderHistoryFailure = (error) => ({
  type: FETCH_ORDER_HISTORY_FAILURE,
  payload: error,
});
 
export const fetchOrderHistory = (userId) => async (dispatch) => {
  dispatch(fetchOrderHistoryRequest());
  try {
    const response = await axios.get(`http://localhost:8086/orders/users/${userId}`);
    dispatch(fetchOrderHistorySuccess(response.data));
  } catch (error) {
    dispatch(fetchOrderHistoryFailure(error.message));
  }
};
 
export const fetchDeliveredHistoryRequest = () => ({
  type: FETCH_DELIVERED_HISTORY_REQUEST,
});
 
export const fetchDeliveredHistorySuccess = (deliveredHistory) => ({
  type: FETCH_DELIVERED_HISTORY_SUCCESS,
  payload: deliveredHistory,
});
 
export const fetchDeliveredHistoryFailure = (error) => ({
  type: FETCH_DELIVERED_HISTORY_FAILURE,
  payload: error,
});
 
export const fetchDeliveredHistory = (userId) => async (dispatch) => {
  dispatch(fetchDeliveredHistoryRequest());
  try {
    const response = await axios.get(`http://localhost:8086/ordersHistory/users/${userId}`);
    dispatch(fetchDeliveredHistorySuccess(response.data));
  } catch (error) {
    dispatch(fetchDeliveredHistoryFailure(error.message));
  }
};
 
export const placeOrder = (orderPayload) => {
  return async (dispatch) => {
    dispatch({ type: PLACE_ORDER_REQUEST });
 
    try {
      const response = await axios.post('http://localhost:8086/orders', orderPayload, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      dispatch({ type: PLACE_ORDER_SUCCESS, payload: response.data });
      dispatch(setCurrentStep(4));
    } catch (error) {
      dispatch({ type: PLACE_ORDER_FAILURE, error: error.message });
      console.error('Error placing order:', error);
    }
  };
};
 
export const setCurrentStep = (step) => ({ type: SET_CURRENT_STEP, payload: step });
export const setSelectedAddress = (address) => ({ type: SET_SELECTED_ADDRESS, payload: address });
export const setOrderData = (data) => ({ type: SET_ORDER_DATA, payload: data });
export const setPaymentMethod = (method) => ({ type: SET_PAYMENT_METHOD, payload: method });
export const setUseDefaultAddress = (useDefault) => ({ type: SET_USE_DEFAULT_ADDRESS, payload: useDefault });
export const setIsEnteringAddressManually = (isEntering) => ({ type: SET_IS_ENTERING_ADDRESS_MANUALLY, payload: isEntering });
export const setSelectedDate = (date) => ({ type: SET_SELECTED_DATE, payload: date });
export const setAddressFields = (fields) => ({ type: SET_ADDRESS_FIELDS, payload: fields });