package com.github.nmorel.hello.gwtp.client;

import com.github.nmorel.hello.gwtp.shared.FieldVerifier;
import com.github.nmorel.hello.gwtp.shared.GreetingRequest;
import com.github.nmorel.hello.gwtp.shared.GreetingResponse;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Nicolas Morel
 */
public class Hello implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " +
            "" + "connection and try again.";

    private static final HelloGinjector ginjector = GWT.create( HelloGinjector.class );

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button( "Send" );
        final TextBox nameField = new TextBox();
        nameField.setText( "GWT User" );
        final Label errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName( "sendButton" );

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get( "nameFieldContainer" ).add( nameField );
        RootPanel.get( "sendButtonContainer" ).add( sendButton );
        RootPanel.get( "errorLabelContainer" ).add( errorLabel );

        // Focus the cursor on the name field when the app loads
        nameField.setFocus( true );
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText( "Remote Procedure Call" );
        dialogBox.setAnimationEnabled( true );
        final Button closeButton = new Button( "Close" );
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId( "closeButton" );
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName( "dialogVPanel" );
        dialogVPanel.add( new HTML( "<b>Sending name to the server:</b>" ) );
        dialogVPanel.add( textToServerLabel );
        dialogVPanel.add( new HTML( "<br><b>Server replies:</b>" ) );
        dialogVPanel.add( serverResponseLabel );
        dialogVPanel.setHorizontalAlignment( VerticalPanel.ALIGN_RIGHT );
        dialogVPanel.add( closeButton );
        dialogBox.setWidget( dialogVPanel );

        // Add a handler to close the DialogBox
        closeButton.addClickHandler( new ClickHandler() {
            public void onClick( ClickEvent event ) {
                dialogBox.hide();
                sendButton.setEnabled( true );
                sendButton.setFocus( true );
            }
        } );

        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {

            /**
             * Fired when the user clicks on the sendButton.
             */
            public void onClick( ClickEvent event ) {
                sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            public void onKeyUp( KeyUpEvent event ) {
                if ( event.getNativeKeyCode() == KeyCodes.KEY_ENTER ) {
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText( "" );
                String textToServer = nameField.getText();
                if ( !FieldVerifier.isValidName( textToServer ) ) {
                    errorLabel.setText( "Please enter at least four characters" );
                    return;
                }

                // Then, we send the input to the server.
                sendButton.setEnabled( false );
                textToServerLabel.setText( textToServer );
                serverResponseLabel.setText( "" );

                ginjector.getRestDispatch().execute( ginjector.getHelloRestService()
                        .greet( new GreetingRequest( textToServer ) ), new AsyncCallback<GreetingResponse>() {
                    @Override
                    public void onFailure( Throwable caught ) {
                        // Show the RPC error message to the user
                        dialogBox.setText( "Remote Procedure Call - Failure" );
                        serverResponseLabel.addStyleName( "serverResponseLabelError" );
                        serverResponseLabel.setHTML( SERVER_ERROR );
                        dialogBox.center();
                        closeButton.setFocus( true );
                    }

                    @Override
                    public void onSuccess( GreetingResponse result ) {
                        dialogBox.setText( "Remote Procedure Call" );
                        serverResponseLabel.removeStyleName( "serverResponseLabelError" );
                        serverResponseLabel.setHTML( new SafeHtmlBuilder().appendEscaped( result.getGreeting() )
                                .appendHtmlConstant( "<br><br>I am running " ).appendEscaped( result.getServerInfo() )
                                .appendHtmlConstant( ".<br><br>It looks like you are using:<br>" ).appendEscaped( result.getUserAgent() )
                                .toSafeHtml() );
                        dialogBox.center();
                        closeButton.setFocus( true );
                    }
                } );
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler( handler );
        nameField.addKeyUpHandler( handler );
    }
}
