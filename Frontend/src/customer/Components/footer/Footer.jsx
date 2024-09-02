import { Grid, Typography } from '@mui/material';

const Footer = () => {
  return (
    <Grid className='bg-black text-white mt-10 text-center' container color={'white'} sx={{ bgcolor: 'black', color: 'white', py: 3 }}>
      <Grid item xs={12}>
        <Typography variant="body2" component="p" gutterBottom>
          © {new Date().getFullYear()} SmallBasket. All rights reserved.
        </Typography>
      </Grid>
    </Grid>
  );
};

export default Footer;
