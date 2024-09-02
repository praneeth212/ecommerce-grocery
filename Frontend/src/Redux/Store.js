/* istanbul ignore file */ 

import { applyMiddleware, combineReducers, legacy_createStore } from "redux";
import thunk from "redux-thunk";
import authReducer from "./Auth/Reducer";
import customerProductReducer from "./Product/Reducer";
import reviewReducer from "../Redux/Review/Reducer";
import cartReducer from "../Redux/Cart/cartReducer";
import checkoutReducer from "./Checkout/Reducer";

const rootReducer = combineReducers({
  auth: authReducer,
  cart: cartReducer,
  customersProduct: customerProductReducer,
  review: reviewReducer,
  checkout: checkoutReducer
});

export const store = legacy_createStore(rootReducer, applyMiddleware(thunk));
