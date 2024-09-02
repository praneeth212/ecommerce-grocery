import * as React from "react";
import { Grid, TextField, Button, Box, Snackbar, Alert } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { getUser, login, resetAuthState } from "../../../Redux/Auth/Action";
import { useEffect, useState } from "react";

export default function LoginUserForm({ handleNext }) {

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const [openSnackBar, setOpenSnackBar] = useState(false);
  const { auth } = useSelector((store) => store);

  const handleCloseSnakbar = () => setOpenSnackBar(false);
  
    useEffect(()=>{
      if(jwt) { dispatch(getUser(jwt)) }
    }, [jwt, dispatch])
  
    useEffect(() => {
      if (auth.user || auth.error) setOpenSnackBar(true)
    }, [auth.user]);

    const handleNavigateToRegister = () => {
      dispatch(resetAuthState()); // Reset the auth state
      navigate("/register");
    };

    const handleSubmit = (event) => {
      event.preventDefault();

      const data = new FormData(event.currentTarget);
      
      const userData = {
        email: data.get("email"),
        password: data.get("password"),
      }
      
      console.log("login user", userData);
    
      dispatch(login(userData));

  };

  return (
    <React.Fragment className=" shadow-lg ">
      <form className="w-full" onSubmit={handleSubmit}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              required
              id="email"
              name="email"
              label="Email"
              fullWidth
              autoComplete="given-name"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              required
              id="password"
              name="password"
              label="Password"
              fullWidth
              autoComplete="given-name"
              type="password"
            />
          </Grid>

          <Grid item xs={12}>
            <Button
              className="bg-[#9155FD] w-full"
              type="submit"
              variant="contained"
              size="large"
              sx={{padding:".8rem 0"}}
            >
              Login
            </Button>
          </Grid>
        </Grid>
      </form>
      
      <div className="flex justify-center flex-col items-center">
         <div className="py-3 flex items-center">
        <p className="m-0 p-0">Don't have an account ?</p>
        <Button onClick={handleNavigateToRegister} className="ml-5" size="small">
          Register
        </Button>
        </div>
      </div>

      <Snackbar open={ openSnackBar } autoHideDuration={6000} onClose={handleCloseSnakbar}>
        <Alert onClose={ handleCloseSnakbar } severity={auth.error ? "error" : "success"} sx={{ width: '100%' }}>
          {auth.error ? auth.error : auth.success}
        </Alert>
      </Snackbar>
    
    </React.Fragment>
  );
}
