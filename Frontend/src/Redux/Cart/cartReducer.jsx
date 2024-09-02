/* istanbul ignore file */ 

const initialState = {
    cartItems: [],
    totalItems: 0,
    totalPrice: 0,
    totalSalePrice: 0,
    notification: '',
};

const cartReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'FETCH_CART_SUCCESS':
            return {
                ...state,
                cartItems: action.payload.cartItems,
                totalItems: action.payload.totalItems,
                totalPrice: action.payload.totalPrice,
                totalSalePrice: action.payload.totalSalePrice,
            };
        case 'ADD_TO_CART_SUCCESS':
            return {
                ...state,
                notification: action.payload,
            };
        case 'UPDATE_CART_ITEM_SUCCESS':
        case 'DELETE_CART_ITEM_SUCCESS':
            return {
                ...state,
                notification: '',
            };
        case 'SET_NOTIFICATION':
            return {
                ...state,
                notification: action.payload,
            };
        default:
            return state;
    }
};

export default cartReducer;



