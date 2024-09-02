import axios from "axios";
import {
  CREATE_REVIEW_REQUEST,
  CREATE_REVIEW_SUCCESS,
  CREATE_REVIEW_FAILURE,
  FETCH_REVIEWS_REQUEST,
  FETCH_REVIEWS_SUCCESS,
  FETCH_REVIEWS_FAILURE,
} from "./ActionType";
 
// Action types
export const createReviewRequest = () => ({ type: CREATE_REVIEW_REQUEST });
export const createReviewSuccess = (review) => ({
  type: CREATE_REVIEW_SUCCESS,
  payload: review,
});
export const createReviewFailure = (error) => ({
  type: CREATE_REVIEW_FAILURE,
  payload: error,
});
 
export const fetchReviewsRequest = () => ({ type: FETCH_REVIEWS_REQUEST });
export const fetchReviewsSuccess = (reviews) => ({
  type: FETCH_REVIEWS_SUCCESS,
  payload: reviews,
});
export const fetchReviewsFailure = (error) => ({
  type: FETCH_REVIEWS_FAILURE,
  payload: error,
});
 
// Thunk for creating review
export const createReview = (reviewData) => async (dispatch) => {
  try {
    const response = await axios.post('http://localhost:8083/api/reviews/create', reviewData);
    dispatch({ type: 'CREATE_REVIEW_SUCCESS', payload: response.data });
  } catch (error) {
    console.error('Error creating review:', error);
    dispatch({ type: 'CREATE_REVIEW_FAILURE', payload: error.message });
  }
};

// Thunk for fetching reviews by product ID
export const fetchReviewsByProductId = (productId) => async (dispatch) => {
  dispatch(fetchReviewsRequest());
  try {
    const response = await axios.get(`http://localhost:8083/api/reviews/product/${productId}`);
    dispatch(fetchReviewsSuccess(response.data));
  } catch (error) {
    dispatch(fetchReviewsFailure(error.message));
  }
};