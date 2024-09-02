import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { Button, Rating } from "@mui/material";
import RateProduct from "../../ReviewProduct/RateProduct";

export default function ProductDetails() {
  const [showRateProduct, setShowRateProduct] = useState(false);
  const [productData, setProductData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isError, setIsError] = useState(false);

  const { productItemId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setIsError(false);

      try {
        const productResponse = await axios.get(`http://localhost:8082/api/products/getdetail/${productItemId}`);
        setProductData(productResponse.data);
      } catch (error) {
        setIsError(true);
        console.error("Error fetching product details:", error);
      } finally {
        setIsLoading(false);
      }
    };

    if (productItemId) {
      fetchData();
    } else {
      setIsLoading(false);
      console.error("No productItemId provided");
    }
  }, [productItemId]);

  const handleAddToCart = () => {
    const cartItem = {
      userId: 5,
      productItemId: productData.productItemId,
      prodName: productData.prodName,
      unitId: productData.unitId,
      unitname: productData.unitname,
      price: productData.price,
      discountPrice: productData.salePrice,
      quantity: 1,
    };
    console.log('cartItem',cartItem)

    axios.post('http://localhost:8084/cart/add', cartItem)
      .then(response => {
        console.log(response.data)
        navigate("/cart");
      })
      .catch(error => {
        console.error('Error adding product to cart:', error);
      });
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (isError || !productData) {
    return <div>Error loading product details</div>;
  }

  return (
    <div className="bg-white lg:px-20">
      <div className="pt-6">
        <nav aria-label="Breadcrumb">
          <ol
            className="mx-auto flex max-w-2xl items-center space-x-2 px-4 sm:px-6 lg:max-w-7xl lg:px-8"
          >
            <li>
              <div className="flex items-center">
                <a
                  href={`/products/${productData.categoryName}`}
                  className="mr-2 text-sm font-medium text-gray-900"
                >
                  {productData.categoryName}
                </a>
                <svg
                  width={16}
                  height={20}
                  viewBox="0 0 16 20"
                  fill="currentColor"
                  aria-hidden="true"
                  className="h-5 w-4 text-gray-300"
                >
                  <path d="M5.697 4.34L8.98 16.532h1.327L7.025 4.341H5.697z" />
                </svg>
              </div>
            </li>
            <li className="text-sm">
              <a href="#"
                aria-current="page"
                className="font-medium text-gray-500 hover:text-gray-600"
              >
                {productData.prodName}
              </a>
            </li>
          </ol>
        </nav>

        {/* product details */}
        <section className="grid grid-cols-1 gap-x-8 gap-y-10 lg:grid-cols-2 px-4 pt-10">
          {/* Image gallery */}
          <div className="flex flex-col items-center ">
            <div className="overflow-hidden rounded-lg max-w-[30rem] max-h-[35rem]">
              <img
                src={`http://localhost:8082${productData.productImg}`}
                alt={productData.prodName}
                className="h-full w-full object-cover object-center"
              />
            </div>
          </div>

          {/* Product info */}
          <div className="lg:col-span-1 mx-auto max-w-2xl px-4 pb-16 sm:px-6 lg:max-w-7xl lg:px-8 lg:pb-24">
            <div className="lg:col-span-2">
              <h1 className="text-lg lg:text-xl font-semibold tracking-tight text-gray-900">
                {productData.prodName}
              </h1>
              <h1 className="text-lg lg:text-xl tracking-tight text-gray-900 opacity-60 pt-1">
                {productData.brand}
              </h1>
            </div>

            <div className="mt-4 lg:row-span-3 lg:mt-0">
              <h2 className="sr-only">Product information</h2>
              <div className="flex space-x-5 items-center text-lg lg:text-xl tracking-tight text-gray-900 mt-6">
                <p className="font-semibold">&#8377;{productData.salePrice}</p>
                <p className="opacity-50 line-through">&#8377;{productData.price}</p>
                <p className='text-green-600 font-semibold'>{productData.discountPercentage.toFixed(2)}% off</p>
              </div>

              <div className="mt-6">
                <h3 className="sr-only">Reviews</h3>
                <div className="flex items-center space-x-3">
                  <Rating name="read-only" value={4.6} precision={0.5} readOnly />
                  <p className="opacity-60 text-sm">42807 Ratings</p>
                  <p className="ml-3 text-sm font-medium text-indigo-600 hover:text-indigo-500">117 reviews</p>
                </div>
              </div>
            </div>

            <div className="py-10 lg:col-span-2 lg:col-start-1 lg:border-r lg:border-gray-200 lg:pb-16 lg:pr-8 lg:pt-6">
              <div>
                <h3 className="sr-only">Description</h3>
                <div className="space-y-6">
                  <p className="text-base text-gray-900">{productData.description}</p>
                </div>
              </div>

              <div className="flex space-x-4 mt-6">
                {productData.quantityInStock > 0 ? (
                  <Button
                    variant="contained"
                    color="primary"
                    sx={{ padding: "0.5rem 2rem" }}
                    onClick={handleAddToCart}
                  >
                    Add to Cart
                  </Button>
                ) : (
                  <p className="text-red-600 font-semibold">Out of Stock</p>
                )}

                <Button
                  variant="contained"
                  color="secondary"
                  sx={{ padding: "0.5rem 2rem" }}
                  onClick={() => setShowRateProduct(true)}
                >
                  Rate and Review Product
                </Button>
              </div>
            </div>
          </div>
        </section>

        {showRateProduct && <RateProduct productId={productData.productId} />}
      </div>
    </div>
  );
}