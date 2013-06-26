package drexel.dragonmap;

import android.app.AlertDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

/*
 * Written by Russell Wiley
 * 
 * 
 */

public class HyperlinkAlertDialog
{
	 public static AlertDialog create( Context context )
	 {
		  final TextView message = new TextView( context );
		  final SpannableString s = new SpannableString( context.getText( R.string.dialog_message ) );
		  Linkify.addLinks( s, Linkify.WEB_URLS );
		  message.setText( s );
		  message.setMovementMethod( LinkMovementMethod.getInstance() );

		  return new AlertDialog.Builder( context )
		   .setTitle( R.string.dialog_title )
		   .setCancelable( true )
		   .setIcon( android.R.drawable.ic_dialog_info )
		   .setPositiveButton( R.string.dialog_action_dismiss, null )
		   .setView( message )
		   .create();
		 }
}
