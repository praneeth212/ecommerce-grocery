import React from "react";
import { Route, Routes, useLocation } from "react-router-dom";
import ProductDetails from "../customer/Components/Product/ProductDetails/ProductDetails";
import Product from "../customer/Components/Product/Product/Product";
import Homepage from "../Pages/Homepage";
import Navigation from "../customer/Components/Navbar/Navigation";
import Crud from "../customer/Components/Cart/crud";
import { ThemeProvider } from '@mui/material/styles';
import { customerTheme } from "../Admin/theme/customeThem";
import Footer from "../customer/Components/footer/Footer";
import SearchProductPage from "../customer/Components/Product/Product/searchproductpage";
import ResetPasswordForm from "../customer/Components/Auth/ResetPasswordForm";
import PasswordChangeSuccess from "../customer/Components/Auth/PasswordChangeSuccess";
import UserProfile from "../customer/Components/Auth/UserProfile";
import CheckoutPage from "../customer/Components/Checkout/CheckoutPage";
import Orders from "../customer/Components/Checkout/Orders";
import ProductTable from "../Admin/componets/Products/productTable";
 
const CustomerRoutes = () => {
    const location = useLocation();
   
    const showNavigation = location.pathname !== "*";
 
  return (
    <div>
      <ThemeProvider theme={customerTheme}>
        {showNavigation && <Navigation />}
        <Routes>
          <Route path="/login" element={<Homepage />}></Route>
          <Route path="/register" element={<Homepage />}></Route>
          <Route path="/forgot" element={<Homepage />}></Route>
          <Route path="/account/reset-password/:token" element={<ResetPasswordForm />} />
          <Route path="/userprofile" element={<UserProfile />}></Route>
          <Route path="/" element={<Homepage />}></Route>
          <Route path="/search/:query" element={<SearchProductPage />} />
          <Route path="/home" element={<Homepage />}></Route>
          <Route path="/products/:categoryName" element={<Product />}></Route>     
          <Route path="/cart" element={<Crud/>}></Route>
          <Route exact path='/password_change_success' element={<PasswordChangeSuccess/>}/>
          <Route path="/admin/products" element={<ProductTable/>} />
          <Route path="/product/:productItemId" element={<ProductDetails />}></Route>
          <Route path="/checkout" element ={<CheckoutPage />}></Route>
          <Route path="/account/order" element={<Orders />}></Route>
        </Routes>
        <Footer/>
      </ThemeProvider>
    </div>
  );
};
 
export default CustomerRoutes;
