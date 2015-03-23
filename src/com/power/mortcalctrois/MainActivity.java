package com.power.mortcalctrois;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
	
	//purchase amt entered by user
	private double purchaseAmt = 0.0;
	//down pay amt entered by user
	private double downPayAmt = 0.0;
	//initial interest rate
	private double interestRate = 0.15;
	//initial length of loan
	private double customLength = 15;
	//shows formatted purchase amt
	private TextView purchaseAmtDisplayTextView;
	//shows formatted down payment
	private TextView downPayDisplayTextView;
	//shows formatted interest rate
	private TextView interestRateDisplayTextView;
	//shows loan total
	private TextView loanAmtDisplayTextView;
	//shows monthly payments total
	private TextView monthlyAmtDisplayTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get references for textviews
		purchaseAmtDisplayTextView = (TextView) findViewById(R.id.purchaseAmtDisplayTextView);
		downPayDisplayTextView = (TextView) findViewById(R.id.downPayDisplayTextView);
		interestRateDisplayTextView = (TextView) findViewById(R.id.interestRateDisplayTextView);
		loanAmtDisplayTextView = (TextView) findViewById(R.id.loanAmtDisplayTextView);
		monthlyAmtDisplayTextView = (TextView) findViewById(R.id.monthlyAmtDisplayTextView);
				
		//set text watchers for purchase, down, interest
		EditText purchaseAmtEditText = (EditText) findViewById(R.id.purchaseAmtEditText);
		purchaseAmtEditText.addTextChangedListener(purchaseAmtTextWatcher);
		EditText downPayEditText = (EditText) findViewById(R.id.downPayEditText);
		downPayEditText.addTextChangedListener(downPayAmtTextWatcher);
		EditText interestRateEditText = (EditText) findViewById(R.id.interestRateEditText);
		interestRateEditText.addTextChangedListener(interestRateTextWatcher);
		
		//set seekbar's listener
		SeekBar customLengthSeekBar = (SeekBar) findViewById(R.id.customLengthSeekBar);
		customLengthSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	}//end onCreate

	private void updateLoanAmt()
	{
		//calculate cost of loan by subtracting DownPayment from Purchase price
		 double loanAmt = purchaseAmt - downPayAmt;
		//display the loan amt
		 loanAmtDisplayTextView.setText(currencyFormat.format(loanAmt));	 
	}//end updateLoanAmt
	
	//monthly payments
	private void updateMonthlyAmt()
	{
		//get loan amount
		double customLoanAmt = purchaseAmt - downPayAmt;
		
		//get years from seek bar
		//monthlyDisplayTextView.setText(String.valueOf(customLength));
		
		//do math for monthly installments. 
		double monthlyPayments = (customLoanAmt/ (12 * customLength) * interestRate); 
		
		//display monthly payments
		monthlyAmtDisplayTextView.setText(currencyFormat.format(monthlyPayments));
		
	}

	//called when user changes position of seekbar
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
	{
		//update customLength
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			//sets customLength to position of seekbar
			customLength = progress;
			updateMonthlyAmt();
		}//end onProgressChanged
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
		}
		
	};//end onSeekBarChangeListener
	
	//event handler for purchaseAmtEditText changes
	private TextWatcher purchaseAmtTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			//convert purchaseAmtEditText's text to a double
			try
			{
				purchaseAmt = Double.parseDouble(s.toString()) / 100.0;
			}//end try
			catch (NumberFormatException e)
			{
				purchaseAmt = 0.0;//default if exception occurs
			}//end catch
			
			//display currency formatted purchase amt
			purchaseAmtDisplayTextView.setText(currencyFormat.format(purchaseAmt));
			updateLoanAmt();
			updateMonthlyAmt();
		}//end onTextChanted
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}
		
	};//end purchaseAmtTextWatcher
	
	//event handler for downPayAmtEditText changes
	private TextWatcher downPayAmtTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			//convert downPayAmtEditText's text to a double
			try
			{
				downPayAmt = Double.parseDouble(s.toString()) / 100.0;
			}//end try
			catch (NumberFormatException e)
			{
				downPayAmt = 0.0;//default if exception occurs
			}//end catch
			
			//display currency formatted purchase amt
			downPayDisplayTextView.setText(currencyFormat.format(downPayAmt));
			updateLoanAmt();
			updateMonthlyAmt();
		}//end onTextChanted
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}
		
	};//end downPayAmtTextWatcher

	//event handler for interestRateEditText changes
	private TextWatcher interestRateTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			//convert interestRateEditText's text to a double
			try
			{
				interestRate = Double.parseDouble(s.toString()) / 100.0;
			}//end try
			catch (NumberFormatException e)
			{
				interestRate = 0.0;//default if exception occurs
			}//end catch
			
			//display currency formatted purchase amt
			interestRateDisplayTextView.setText(percentFormat.format(interestRate));
			updateLoanAmt();
			updateMonthlyAmt();
		}//end onTextChanted
		
		@Override
		public void afterTextChanged(Editable s)
		{
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}
		
	};//end interestRateTextWatcher

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
