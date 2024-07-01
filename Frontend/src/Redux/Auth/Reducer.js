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
  RESET_PASSWORD_REQUEST,
  REQUEST_RESET_PASSWORD_REQUEST,
  REQUEST_RESET_PASSWORD_SUCCESS,
  REQUEST_RESET_PASSWORD_FAILURE,
  GET_ALL_CUSTOMERS_SUCCESS,
  // UPDATE_USER_FAILURE,
  // UPDATE_USER_SUCCESS,
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
    case GET_USER_REQUEST:
    case RESET_PASSWORD_REQUEST:
    case REQUEST_RESET_PASSWORD_REQUEST:
      return { ...state, isLoading: true, error: null,success:null };
    case REGISTER_SUCCESS:
      return { ...state, isLoading: false , jwt: action.payload,success: "Register Success",};
    case REGISTER_FAILURE:
    case LOGIN_FAILURE:
    case GET_USER_FAILURE:
    case REQUEST_RESET_PASSWORD_FAILURE:
      return { ...state, isLoading: false, error: action.payload };
    case LOGIN_SUCCESS:
      return { ...state, isLoading: false, jwt: action.payload,};
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
    case GET_ALL_CUSTOMERS_SUCCESS:
      return {
          ...state,
          isLoading: false,
          customers: action.payload,
        };
    case GET_USER_FAILURE:
      return {
        ...state,
        isLoading: false,
        error: action.payload,
        fetchingUser: false,
      };

      // case UPDATE_USER_SUCCESS:
      //   return { ...state, 
      //     isLoading: false, 
      //     user: action.payload };
      // case UPDATE_USER_FAILURE:
      //   return { ...state, 
      //     isLoading: false, 
      //     error: action.payload };

    case LOGOUT:
      localStorage.removeItem("jwt");
      return { ...state, jwt: null, user: null };
    default:
      return state;
  }
};

export default authReducer;
