package com.cbs.ghgroup.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cbs.ghgroup.R;
import com.cbs.ghgroup.adapter.CheckboxTypesAdapter;
import com.cbs.ghgroup.databinding.ActivityOrderReportDetailsBinding;
import com.cbs.ghgroup.model.typescheckbox.CheckboxTypes;
import com.cbs.ghgroup.utils.CommonMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderReportDetails extends AppCompatActivity {
    ActivityOrderReportDetailsBinding orderReportDetailsBinding;
    Context mContext;
    TextView tvORDropdown;
    boolean[] selectedDropDown;
    ArrayList<Integer> codeList = new ArrayList<>();
    private CheckboxTypes strCheckbox;
    final String[] select_spinner = {"","Vendor Code", "Vendor Name", "BP Code", "Customer Name"};
    public int mYear, mMonth, mDay;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderReportDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_order_report_details);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        mContext = OrderReportDetails.this;

        getId();
    }

    private  void  getId(){
        ImageView ivBack = findViewById(R.id.img_BackButton);
        TextView setActivityName = findViewById(R.id.txtHeader);
        setActivityName.setText(R.string.order_report);

        /*final String[] select_spinner = {
                "", "Vendor Code", "Vendor Name", "BP Code", "Customer Name"};*/
        Spinner spinner = (Spinner) findViewById(R.id.spinner_OR_types);

        tvORDropdown = findViewById(R.id.tv_OR_dropdown);

        selectedDropDown = new boolean[select_spinner.length];

        tvORDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderReportDetails.this);
                builder.setTitle("Select");
                builder.setCancelable(false);
                tvORDropdown.setText(builder.toString());
                builder.show();
            }
        });

        ArrayList<CheckboxTypes> checkboxList = new ArrayList<>();

        for (int i = 0; i < select_spinner.length; i++) {
            CheckboxTypes checkboxTypes = new CheckboxTypes();
            checkboxTypes.setTitle(select_spinner[i]);
            checkboxTypes.setSelected(false);
            checkboxList.add(checkboxTypes);

        }

        CheckboxTypesAdapter checkboxTypesAdapter = new CheckboxTypesAdapter(OrderReportDetails.this, 0, checkboxList);
        spinner.setAdapter(checkboxTypesAdapter);

       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                       // arg0.setSelection(0);
                       // int position = spinner.getSelectedItemPosition();
                        //Toast.makeText(getApplicationContext(),select_spinner[+position], Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });*/

        orderReportDetailsBinding.tvORFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedyear, selectedmonth, selectedday);
                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        orderReportDetailsBinding.tvORFromDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        orderReportDetailsBinding.tvORToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        Calendar c = Calendar.getInstance();
                        c.set(selectedyear, selectedmonth, selectedday);
                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        orderReportDetailsBinding.tvORToDate.setText(CommonMethods.pad(selectedday) + "/" + CommonMethods.pad(selectedmonth + 1) +
                                "/" + CommonMethods.pad(selectedyear));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setCalendarViewShown(false);
                mDatePicker.show();
            }

        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}