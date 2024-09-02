import {
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  REGISTER_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  GET_USER_REQUEST,
  GET_USER_SUCCESS,
  GET_USER_FAILURE,
  LOGOUT,
  REQUEST_RESET_PASSWORD_REQUEST,
  REQUEST_RESET_PASSWORD_SUCCESS,
  REQUEST_RESET_PASSWORD_FAILURE,
  RESET_AUTH_STATE,
} from "./ActionTypes";
 
const initialState = {
  user: null,
  isLoading: false,
  jwt: null,
  error: null,
  customers: [],
  success: null,
};
 
const authReducer = (state = initialState, action) => {
  switch (action.type) {
    case REGISTER_REQUEST:
    case LOGIN_REQUEST:
    case REQUEST_RESET_PASSWORD_REQUEST:
      return { ...state, isLoading: true, error: null, success:null };
   
    case REGISTER_SUCCESS:
      return { ...state, isLoading: false , jwt: action.payload, success: "Register Success"};
 
    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case REQUEST_RESET_PASSWORD_FAILURE:
      return { ...state, isLoading: false, error: action.payload, success: null};
   
    case LOGIN_SUCCESS:
      return { ...state, isLoading: false, jwt: action.payload};
   
    case GET_USER_REQUEST:
      return { ...state, isLoading: true, error: null, fetchingUser: true };
   
    case GET_USER_SUCCESS:
      return {
        ...state,
        isLoading: false,
        user: action.payload,
        fetchingUser: false,
      };
    case REQUEST_RESET_PASSWORD_SUCCESS:
      return {
        ...state,
        isLoading: false,
        success: action.payload?.message,
      };
     
    case GET_USER_FAILURE:
      return {
        ...state,
        isLoading: false,
        error: action.payload,
        fetchingUser: false,
        success: null
      };
 
      case LOGOUT:
        localStorage.removeItem("jwt");
        return { ...state, jwt: null, user: null };
      case RESET_AUTH_STATE:
        return { ...state, error: null, success: null };
      default:
        return state;
  }
};
 
export default authReducer;