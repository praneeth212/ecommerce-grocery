import {
    SET_CURRENT_STEP,
    SET_SELECTED_ADDRESS,
    SET_ORDER_DATA,
    SET_PAYMENT_METHOD,
    SET_DEFAULT_ADDRESS,
    SET_USE_DEFAULT_ADDRESS,
    SET_IS_ENTERING_ADDRESS_MANUALLY,
    SET_CART,
    SET_TOTAL_COST,
    SET_SELECTED_DATE,
    SET_ADDRESS_FIELDS,
    FETCH_ORDER_HISTORY_REQUEST,
    FETCH_ORDER_HISTORY_SUCCESS,
    FETCH_ORDER_HISTORY_FAILURE,
    FETCH_DELIVERED_HISTORY_REQUEST,
    FETCH_DELIVERED_HISTORY_SUCCESS,
    FETCH_DELIVERED_HISTORY_FAILURE,
    PLACE_ORDER_REQUEST,
    PLACE_ORDER_SUCCESS,
    PLACE_ORDER_FAILURE
  } from './actions';
   
  const initialState = {
    currentStep: 1,
    selectedAddress: '',
    orderData: null,
    paymentMethod: '',
    defaultAddress: null,
    useDefaultAddress: false,
    isEnteringAddressManually: false,
    orderHistory: [],
    orderHistoryLoading: false,
    orderHistoryError: null,
    deliveredHistory: [],
    deliveredHistoryLoading: false,
    deliveredHistoryError: null,
    cart: [],
    totalCost: 0,
    selectedDate: new Date(new Date().getTime() + 24 * 60 * 60 * 1000), // Minimum selectable date is current date + 1 day
    addressFields: {
      address_line1: '',
      address_line2: '',
      city: '',
      country: '',
      state: '',
      zipcode: '',
      loading: false,
      orderData: null,
      error: null,
    }
  };
   
  const checkoutReducer = (state = initialState, action) => {
    switch (action.type) {
      case SET_CURRENT_STEP:
        return { ...state, currentStep: action.payload };
      case SET_SELECTED_ADDRESS:
        return { ...state, selectedAddress: action.payload };
      case SET_ORDER_DATA:
        return { ...state, orderData: action.payload };
      case SET_PAYMENT_METHOD:
        return { ...state, paymentMethod: action.payload };
      case SET_DEFAULT_ADDRESS:
        return { ...state, defaultAddress: action.payload };
      case SET_USE_DEFAULT_ADDRESS:
        return { ...state, useDefaultAddress: action.payload };
      case SET_IS_ENTERING_ADDRESS_MANUALLY:
        return { ...state, isEnteringAddressManually: action.payload };
      case SET_CART:
        return { ...state, cart: action.payload };
      case SET_TOTAL_COST:
        return { ...state, totalCost: action.payload };
      case SET_SELECTED_DATE:
        return { ...state, selectedDate: action.payload };
      case SET_ADDRESS_FIELDS:
        return { ...state, addressFields: action.payload };
      case FETCH_ORDER_HISTORY_REQUEST:
        return {
          ...state,
          orderHistoryLoading: true,
          orderHistoryError: null,
        };
      case FETCH_ORDER_HISTORY_SUCCESS:
        return {
          ...state,
          orderHistory: action.payload,
          orderHistoryLoading: false,
          orderHistoryError: null,
        };
      case FETCH_ORDER_HISTORY_FAILURE:
        return {
          ...state,
          orderHistory: [],
          orderHistoryLoading: false,
          orderHistoryError: action.payload,
        };
      case FETCH_DELIVERED_HISTORY_REQUEST:
        return {
          ...state,
          deliveredHistoryLoading: true,
          deliveredHistoryError: null,
        };
      case FETCH_DELIVERED_HISTORY_SUCCESS:
        return {
          ...state,
          deliveredHistory: action.payload,
          deliveredHistoryLoading: false,
          deliveredHistoryError: null,
        };
      case FETCH_DELIVERED_HISTORY_FAILURE:
        return {
          ...state,
          deliveredHistory: [],
          deliveredHistoryLoading: false,
          deliveredHistoryError: action.payload,
        };
        case PLACE_ORDER_REQUEST:
        return {
          ...state,
          loading: true,
          error: null,
        };
      case PLACE_ORDER_SUCCESS:
        return {
          ...state,
          loading: false,
          orderData: action.payload,
        };
      case PLACE_ORDER_FAILURE:
        return {
          ...state,
          loading: false,
          error: action.error,
        };
      default:
        return state;
    }
  };
   
  export default checkoutReducer;