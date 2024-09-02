import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import RegisterUserForm from "./Register";
import { useEffect, useState } from "react";
import LoginUserForm from "./Login";
import { useLocation,useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { Alert, Snackbar } from "@mui/material";
import ResetPasswordRequest from "./ResetPaswordRequest";
import ResetPasswordForm from "./ResetPasswordForm";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
};

export default function AuthModal({ handleClose, open }) {
  const location = useLocation();
  const navigate = useNavigate();
  const [openSnackBar,setOpenSnackBar]=useState(false);
  const { auth } = useSelector((store) => store);
  
const handleCloseSnackBar=()=>{
  setOpenSnackBar(false)
}
  useEffect(()=>{
    if(auth.success || auth.error)setOpenSnackBar(true)
    },[auth.success, auth.error])
  return (
    <>
    <Modal
     open={ location.pathname === "/register" ||
     location.pathname === "/login" ||
     location.pathname === "/account/reset-password-request" ||
     location.pathname === "/account/reset-password"}
    onClose={handleClose}
      >
      <Box className="rounded-md" sx={style}>
         {location.pathname === "/register" ? (
              <RegisterUserForm />
          ) : location.pathname === "/login" ? (
            <LoginUserForm  />   
          )  : location.pathname === "/account/reset-password" ? <ResetPasswordForm/>: (
            <ResetPasswordRequest />
          )}
        <div className="flex justify-center mt-5">
            {location.pathname === "/account/reset-password-request" || location.pathname === "/account/reset-password"  ? (
              <Button onClick={() => navigate("/login")}>
                Go Back To Login
              </Button>
            ) : (
              <Button
                onClick={() => navigate("/account/reset-password-request")}
              >Forgot Pasword
              </Button>
            )}
             <Snackbar
              sx={{ zIndex: 50 }}
              open={openSnackBar}
              autoHideDuration={3000}
              onClose={handleCloseSnackBar}
              handleClose={handleCloseSnackBar}
              anchorOrigin={{ vertical: "top", horizontal: "right" }}
            >
                <Alert severity={auth.error?"error":"success"} sx={{ width: "100%" }}>
                {auth.success || auth.error}
              </Alert>
            </Snackbar>
            </div>
      </Box>
    </Modal>
    
    </>
    
  );
}
