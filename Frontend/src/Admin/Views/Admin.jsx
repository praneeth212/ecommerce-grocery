// ** MUI Imports
/* istanbul ignore file */ 

import Grid from "@mui/material/Grid";
import AdminPannel from "../../Styles/AdminPannelWrapper";
import { ThemeProvider, createTheme } from "@mui/material";
import { customTheme, customerTheme, darkTheme } from "../theme/customeThem";
import "./Admin.css";
import {AssuredWorkloadIcon }from '@mui/icons-material';
import { BriefcaseVariantOutline, CurrencyUsd, HelpCircleOutline, Poll } from "mdi-material-ui";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const darkTheme1 = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#312d4b',
    },
    secondary: {
      main: '#f48fb1',
    },
  },
});
// bg-[#28243d]
const Dashboard = () => {
  const {auth}=useSelector(store=>store);
  const navigate=useNavigate()
  return (
    <div className="adminContainer ">
      <ThemeProvider theme={customerTheme}>
        <AdminPannel>
          <Grid container spacing={2}>   
          </Grid>
        </AdminPannel>
        </ThemeProvider>
    </div>
  );
};

export default Dashboard;
