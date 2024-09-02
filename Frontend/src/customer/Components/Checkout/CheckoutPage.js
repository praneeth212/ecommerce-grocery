import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import 'react-datepicker/dist/react-datepicker.css';
import {
  fetchDefaultAddress,
  fetchCart,
  setCurrentStep,
  setSelectedAddress,
  setUseDefaultAddress,
  setIsEnteringAddressManually,
  setAddressFields,
  setPaymentMethod,
  setSelectedDate,
  placeOrder,
} from '../../../Redux/Checkout/actions';
import DatePicker from 'react-datepicker';
import {
  Button,
  Radio,
  RadioGroup,
  FormControlLabel,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from '@mui/material';
 
function CheckoutPage() {
  const dispatch = useDispatch();
  const userId = useSelector((state) => state.auth.user.id);
  const navigate=useNavigate();
 
  const {
    currentStep,
    selectedAddress,
    orderData,
    paymentMethod,
    defaultAddress,
    useDefaultAddress,
    cart,
    selectedDate,
    addressFields,
  } = useSelector((state) => state.checkout); // Corrected state access
 
  const currentDate = new Date();
  const minDate = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000);
  const maxDate = new Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000);
 
  const handleDateChange = (date) => {
    dispatch(setSelectedDate(date));
  };
 
  useEffect(() => {
    dispatch(fetchCart());
  }, [dispatch]);
 
  useEffect(() => {
    if (userId){
    dispatch(fetchDefaultAddress(userId));
    }
  }, [dispatch, userId]);
 
 
 
  const handleProceedToReview = () => {
    dispatch(setCurrentStep(2));
  };
 
  const handleProceedToPayment = () => {
    dispatch(setCurrentStep(3));
  };
 
  const handleAddressSelection = () => {
    if (useDefaultAddress) {
      dispatch(setSelectedAddress(defaultAddress));
    } else {
      dispatch(setSelectedAddress(addressFields));
    }
    handleProceedToReview();
  };
 
  const handlePaymentMethodSelection = (method) => {
    dispatch(setPaymentMethod(method));
  };
 
  const handlePlaceOrder = () => {
 
    const items = cart.map(item => ({
      unit: item.productItem.unit.unitName,
      quantity: item.quantity,
      price: item.price,
      discountedPrice: item.discountPrice,
      product: { id: item.productItem.id },
      deliveryDate: selectedDate
    }));
 
     const totalPrice = cart.reduce((accumulator,item)=> accumulator+item.discountPrice,0);
     const discount = cart.reduce((accumulator,item)=>accumulator+ item.price,0)-totalPrice;
     const totalItem = cart.reduce((accumulator,item)=>accumulator+item.quantity,0);
 
 
    const orderPayload = {
      userId: userId,
      discount: discount,
      totalPrice: totalPrice,
      orderDate: new Date(),
      totalItem: totalItem,
      status: "Pending",
      items: items
    };
 
    dispatch(placeOrder(orderPayload));
  };
 
  const handleAddressInputChange = (e) => {
    const { id, value } = e.target;
    dispatch(
      setAddressFields({
        ...addressFields,
        [id]: value,
      })
    );
    dispatch(setIsEnteringAddressManually(true));
    dispatch(setUseDefaultAddress(false));
  };

  const handleShopping = () => {        
    navigate('/');
    window.location.reload();
  };
 
  const renderStepContent = () => {
    switch (currentStep) {
      case 1:
        return (
          <div className="space-y-4">
            <h1 className="text-2xl font-bold mb-4">Checkout</h1>
            <h2 className="text-xl font-semibold mb-4">Delivery Address</h2>
            <RadioGroup value={useDefaultAddress ? 'default' : 'manual'} onChange={(e) => dispatch(setUseDefaultAddress(e.target.value === 'default'))}>
              <FormControlLabel value="default" control={<Radio />} label="Use default address" />
              <p className="mb-4">
                Default Address: {defaultAddress?.address_line1}, {defaultAddress?.address_line2}, {defaultAddress?.city}, {defaultAddress?.zipcode}, {defaultAddress?.state}, {defaultAddress?.country}
              </p>
              <FormControlLabel value="manual" control={<Radio />} label="Enter new address manually" />
            </RadioGroup>
            {!useDefaultAddress && (
              <>
                <TextField fullWidth label="Address Line 1" id="addressLine1" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                <TextField fullWidth label="Address Line 2" id="addressLine2" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <TextField fullWidth label="City" id="city" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                  <TextField fullWidth label="Zipcode" id="zipcode" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                  <TextField fullWidth label="State" id="state" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                  <TextField fullWidth label="Country" id="country" variant="outlined" onChange={handleAddressInputChange} margin="normal" />
                </div>
              </>
            )}
            <Button variant="contained" color="primary" onClick={handleAddressSelection} className="mt-6">
              Proceed
            </Button>
          </div>
        );
      case 2:
        return (
          <div>
            <h1 className="text-2xl font-bold mb-4">Checkout</h1>
            <h2 className="text-xl font-semibold mb-4">Review Orders</h2>
            <p className="mb-4">
              Selected Address: {selectedAddress?.address_line1}{selectedAddress?.addressLine1}, {selectedAddress?.address_line2}{selectedAddress?.addressLine2}, {selectedAddress?.city}, {selectedAddress?.zipcode}, {selectedAddress?.state}, {selectedAddress?.country}
            </p>
            <TableContainer component={Paper} className="mb-4">
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Product Name</TableCell>
                    <TableCell>Price</TableCell>
                    <TableCell>Quantity</TableCell>
                    <TableCell>Discount</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {cart.map((item, index) => (
                    <TableRow key={index}>
                      <TableCell>{item.prodName}</TableCell>
                      <TableCell>${item.price}</TableCell>
                      <TableCell>{item.quantity}</TableCell>
                      <TableCell>${item.discountPrice}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <Button variant="contained" color="primary" onClick={handleProceedToPayment} className="mt-6">
              Proceed
            </Button>
          </div>
        );
      case 3:
        return (
          <div>
            <h1 className="text-2xl font-bold mb-4">Checkout</h1>
            <h2 className="text-xl font-semibold mb-4">Payment</h2>
            <h4>Select the delivery date</h4>
            <DatePicker
              selected={selectedDate}
              onChange={handleDateChange}
              minDate={minDate}
              maxDate={maxDate}
              className="form-control text-center w-full p-2 border rounded mb-4"
            />
            <h4>Payment Mode</h4>
            <RadioGroup value={paymentMethod} onChange={(e) => handlePaymentMethodSelection(e.target.value)}>
              <FormControlLabel value="UPI" control={<Radio />} label="UPI" />
              <FormControlLabel value="Credit Card" control={<Radio />} label="Credit Card" />
              <FormControlLabel value="Cash on Delivery" control={<Radio />} label="Cash on Delivery" />
            </RadioGroup>
            <Button variant="contained" color="primary" onClick={handlePlaceOrder} className="mt-6" disabled={!paymentMethod}>
              Place Order
            </Button>
          </div>
        );
      case 4:
        return (
          <div>
            <h2 className="text-2xl font-bold mb-4">Congratulations!!!</h2>
            <h2 className="text-xl font-semibold mb-4">Your order is placed</h2>
            {orderData && (
              <div className="mt-4">
              <Button variant="contained" color="primary" onClick={handleShopping } className="mt-6" disabled={!paymentMethod}>
              Continue Shopping
            </Button>
              </div>
            )}
          </div>
        );
      default:
        return null;
    }
  };
 
  return (
    <div className="container mx-auto px-4">
      <div className="main-content justify-center py-4">
        {renderStepContent()}
      </div>
    </div>
  );
}
 
export default CheckoutPage;