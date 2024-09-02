import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import {
    setCurrentStep,
    fetchOrderHistory,
  fetchDeliveredHistory,
} from '../../../Redux/Checkout/actions';
import {
  Button,
  CircularProgress,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableContainer,
  TableRow,
  Paper,
} from '@mui/material';
 
function Orders() {
  const dispatch = useDispatch();
 
  const userId = useSelector((state) => state.auth.user.id);
 
  useEffect(() => {
    if(userId)
      dispatch(fetchOrderHistory(userId));
      dispatch(fetchDeliveredHistory(userId));
  }, [dispatch, userId]);
 
  const {
    currentStep,
    orderHistory,
    orderHistoryLoading,
    orderHistoryError,
    deliveredHistory,
    deliveredHistoryLoading,
    deliveredHistoryError,
  } = useSelector((state) => state.checkout); // Corrected state access
 
  const renderOrderHistory = () => {
    switch (currentStep) {
      case 1:
        return (
          <div>
            <h2 className="text-2xl font-bold mb-4">Order History</h2>
            {orderHistoryLoading ? (
              <CircularProgress />
            ) : orderHistoryError ? (
              <p>Error fetching order history: {orderHistoryError}</p>
            ) : (
              <div>
                {orderHistory?.map((order, index) => (
                  <div key={index} className="mb-6">
                    <TableContainer component={Paper} className="mb-4">
                      <Table>
                        <TableBody>
                          <TableRow>
                            <TableCell>Status:</TableCell>
                            <TableCell>{order.status}</TableCell>
                            <TableCell>Total Items:</TableCell>
                            <TableCell>{order.totalItem}</TableCell>
                            <TableCell>Total Price:</TableCell>
                            <TableCell>{order.totalPrice}</TableCell>
                            <TableCell>Ordered Date:</TableCell>
                            <TableCell>{order.orderDate}</TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>
                    </TableContainer>
                    <TableContainer component={Paper} className="mb-4">
                      <Table>
                        <TableHead>
                          <TableRow>
                            <TableCell>Product Name</TableCell>
                            <TableCell>Unit</TableCell>
                            <TableCell>Discounted Price</TableCell>
                            <TableCell>Price</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {order.items.map((item, index) => (
                            <TableRow key={index}>
                              <TableCell>{item.productName}</TableCell>
                              <TableCell>{item.unit}</TableCell>
                              <TableCell>{item.discountedPrice}</TableCell>
                              <TableCell>{item.price}</TableCell>
                            </TableRow>
                          ))}
                        </TableBody>
                      </Table>
                    </TableContainer>
                    <div style={{ marginBottom: '1rem' }}></div>
                  </div>
                ))}
              </div>
            )}
            <Button variant="contained" color="primary" onClick={() => dispatch(setCurrentStep(2))} className="mt-6">
              View Delivered Orders
            </Button>
          </div>
        );
      case 2:
        return (
          <div>
            <h2 className="text-2xl font-bold mb-4">Delivered Order History</h2>
            {deliveredHistoryLoading ? (
              <CircularProgress />
            ) : deliveredHistoryError ? (
              <p>Error fetching delivered history: {deliveredHistoryError}</p>
            ) : (
              <div>
                {deliveredHistory?.map((order, index) => (
                  <div key={index} className="mb-6">
                    <TableContainer component={Paper} className="mb-4">
                      <Table>
                        <TableBody>
                          <TableRow>
                            <TableCell>Status:</TableCell>
                            <TableCell>{order.status}</TableCell>
                            <TableCell>Total Items:</TableCell>
                            <TableCell>{order.totalItem}</TableCell>
                            <TableCell>Total Price:</TableCell>
                            <TableCell>{order.totalPrice}</TableCell>
                            <TableCell>Ordered Date:</TableCell>
                            <TableCell>{order.orderDate}</TableCell>
                          </TableRow>
                        </TableBody>
                      </Table>
                    </TableContainer>
                    <TableContainer component={Paper} className="mb-4">
                      <Table>
                        <TableHead>
                          <TableRow>
                            <TableCell>Product Name</TableCell>
                            <TableCell>Unit</TableCell>
                            <TableCell>Discounted Price</TableCell>
                            <TableCell>Price</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {order.items.map((item, index) => (
                            <TableRow key={index}>
                              <TableCell>{item.productName}</TableCell>
                              <TableCell>{item.unit}</TableCell>
                              <TableCell>{item.discountedPrice}</TableCell>
                              <TableCell>{item.price}</TableCell>
                            </TableRow>
                          ))}
                        </TableBody>
                      </Table>
                    </TableContainer>
                    <div style={{ marginBottom: '1rem' }}></div>
                  </div>
                ))}
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
        {renderOrderHistory()}
      </div>
    </div>
  );
}
 
export default Orders;