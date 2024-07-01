import axios from 'axios';
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
  GET_ALL_CUSTOMERS_REQUEST,
  GET_ALL_CUSTOMERS_SUCCESS,
  GET_ALL_CUSTOMERS_FAILURE,
  REQUEST_RESET_PASSWORD_FAILURE,
  REQUEST_RESET_PASSWORD_REQUEST,
  REQUEST_RESET_PASSWORD_SUCCESS,
  UPDATE_USER_FAILURE,
  UPDATE_USER_SUCCESS,

} from './ActionTypes';
import api, { API_BASE_URL } from '../../config/api';

// Register action creators
const registerRequest = () => ({ type: REGISTER_REQUEST });
const registerSuccess = (user) => ({ type: REGISTER_SUCCESS, payload:user });
const registerFailure = error => ({ type: REGISTER_FAILURE, payload: error });

export const register = userData => async dispatch => {
  dispatch(registerRequest());
  try {
    const response=await axios.post(`${API_BASE_URL}/auth/signup`, userData);
    const user = response.data;
    if(user.jwt) localStorage.setItem("jwt",user.jwt)
    console.log("registerr :- ",user)
    dispatch(registerSuccess(user));
  } catch (error) {
    console.log("error ",error)
    dispatch(registerFailure(error.message));
  }
};

// Login action creators
const loginRequest = () => ({ type: LOGIN_REQUEST });
const loginSuccess = user => ({ type: LOGIN_SUCCESS, payload: user });
const loginFailure = error => ({ type: LOGIN_FAILURE, payload: error });

export const login = userData => async dispatch => {
  dispatch(loginRequest());
  try {
    const response = await axios.post(`${API_BASE_URL}/auth/signin`, userData);
    const user = response.data;
    if(user.jwt) localStorage.setItem("jwt",user.jwt)
    console.log("login ",user)
    dispatch(loginSuccess(user));
  } catch (error) {
    dispatch(loginFailure(error.message));
  }
};



//  get user from token
export const getAllCustomers = (token) => {
  return async (dispatch) => {
    console.log("jwt - ",token)
    dispatch({ type: GET_ALL_CUSTOMERS_REQUEST });
    try {
      const response = await axios.get(`${API_BASE_URL}/api/admin/users`,{
        headers:{
          "Authorization":`Bearer ${token}`
        }
      });
      const users = response.data;
      dispatch({ type: GET_ALL_CUSTOMERS_SUCCESS, payload: users });
      console.log("All Customers",users)
    } catch (error) {
      const errorMessage = error.message;
      console.log(error)
      dispatch({ type: GET_ALL_CUSTOMERS_FAILURE, payload: errorMessage });
    }
  };
};

export const getUser = (token) => {
  return async (dispatch) => {
    dispatch({ type: GET_USER_REQUEST });
    try {
      const response = await axios.get(`${API_BASE_URL}/api/users/profile`,{
        headers:{
          "Authorization":`Bearer ${token}`
        }
      });
      const user = response.data;
      dispatch({ type: GET_USER_SUCCESS, payload: user });
      console.log("req User ",user)
    } catch (error) {
      const errorMessage = error.message;
      dispatch({ type: GET_USER_FAILURE, payload: errorMessage });
    }
  };
};

export const resetPasswordRequest = (email) => async (dispatch) => {
  dispatch({type:REQUEST_RESET_PASSWORD_REQUEST});
  try {
    const {data} = await axios.post(`${API_BASE_URL}/auth/reset-password-request?email=${email}`,{});
    
    console.log("reset password -: ", data);
   
    dispatch({type:REQUEST_RESET_PASSWORD_SUCCESS,payload:data});
  } catch (error) {
    console.log("error ",error)
    dispatch({type:REQUEST_RESET_PASSWORD_FAILURE,payload:error.message});
  }
};

export const resetPassword = (reqData) => async (dispatch) => {
  dispatch({type:REQUEST_RESET_PASSWORD_REQUEST});
  try {
    const {data} = await axios.post(`${API_BASE_URL}/auth/reset-password`,reqData.data);
    
    console.log("reset password -: ", data);

    reqData.navigate("/password-change-success")
   
    dispatch({type:REQUEST_RESET_PASSWORD_SUCCESS,payload:data});
  } catch (error) {
    console.log("error ",error)
    dispatch({type:REQUEST_RESET_PASSWORD_FAILURE,payload:error.message});
  }
};

export const logout = (token) => {
    return async (dispatch) => {
      dispatch({ type: LOGOUT });
      localStorage.clear();
    };
  };

// Update user profile
// export const updateUserProfile = (token, user) => async (dispatch) => {
//   try {
//     const response = await axios.put(`${API_BASE_URL}/api/users/profile`, user, {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     dispatch({ type: UPDATE_USER_SUCCESS, payload: response.data });
//   } catch (error) {
//     dispatch({ type: UPDATE_USER_FAILURE, payload: error.message });
//   }
// };