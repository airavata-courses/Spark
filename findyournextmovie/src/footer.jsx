import React, {Component} from 'react';
import Typography from '@material-ui/core/Typography';

class Footer extends Component{
  render(){
    return(
      <div>
      <Typography variant="h6" align="center" gutterBottom>
        Footer
      </Typography>
      <Typography variant="subtitle1" align="center" color="textSecondary" component="p">
        Something here to give the footer a purpose!
      </Typography>
      </div>
    );
  }
}

export default Footer;
