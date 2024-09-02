/* istanbul ignore file */ 
import React, { useEffect, useState } from "react";
import { Button, Grid, TextField, useMediaQuery } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { findProductById } from "../../../Redux/Product/Action";
import { createReview, fetchReviewsByProductId } from "../../../Redux/Review/Action";

const RateProduct = () => {
  const [formData, setFormData] = useState({ email: "", description: "" });
  const isLargeScreen = useMediaQuery("(min-width:1200px)");
  const dispatch = useDispatch();
  const { productItemId } = useParams();
  const navigate = useNavigate();
  const reviews = useSelector((state) => state.review.reviews);
  const productData = useSelector((state) => state.customersProduct.product); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (productData && productData.productId) {
      const reviewData = {
        productId: productData.productId,
        email: formData.email,
        review: formData.description,
      };

      dispatch(createReview(reviewData));
      setFormData({ email: "", description: "" });
      navigate(`/product/${productData.productId}`);
    } else {
      console.error("Product ID is not available");
    }
  };

  useEffect(() => {
    if (productItemId) {
      dispatch(findProductById({ productItemId }));
      dispatch(fetchReviewsByProductId(productItemId));
    }
  }, [dispatch, productItemId]);

  return (
    <div className="px-5 lg:px-20">
      <h1 className="text-xl p-5 shadow-lg mb-8 font-bold">
        Rate & Review Product
      </h1>
      <Grid item xs={12} lg={6}>
        <div className={`${!isLargeScreen ? "py-10" : ""} space-y-5`}>
          <form
            onSubmit={handleSubmit}
            className="space-y-5 p-5 shadow-md border rounded-md"
          >
            <TextField
              label="Email"
              variant="outlined"
              fullWidth
              margin="normal"
              value={formData.email}
              onChange={handleChange}
              name="email"
            />
            <TextField
              label="Description"
              variant="outlined"
              fullWidth
              margin="normal"
              multiline
              rows={4}
              value={formData.description}
              onChange={handleChange}
              name="description"
            />
            <Button type="submit" variant="contained" color="primary">
              Submit Review
            </Button>
          </form>
        </div>
        <div>
          <h2>Product Reviews</h2>
          {reviews.map((review) => (
            <div key={review.id}>
              <p><strong>{review.user.email}</strong>: {review.review}</p>
            </div>
          ))}
        </div>
      </Grid>
    </div>
  );
};

export default RateProduct;
