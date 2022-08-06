/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:58 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

//    AppCompatButton login_with_number, login_with_email, login;
//    AppCompatEditText login_with_email_edittext, login_with_number_edittext, password;
//    AppCompatTextView register, skip;
//    boolean isEmailLogin = true;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.login_fragment, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        findViews(view);
//        start();
//    }
//
//    private void start() {
//
//        login_with_number.setOnClickListener(v -> {
//            isEmailLogin = false;
//            login_with_email_edittext.setVisibility(View.INVISIBLE);
//            login_with_number_edittext.setVisibility(View.VISIBLE);
//        });
//
//        login_with_email.setOnClickListener(v -> {
//            isEmailLogin = true;
//            login_with_number_edittext.setVisibility(View.INVISIBLE);
//            login_with_email_edittext.setVisibility(View.VISIBLE);
//        });
//
//        register.setOnClickListener(v -> ((RegisterActivity) requireContext()).loadFragment(2));
//
//        skip.setOnClickListener(v -> ((RegisterActivity) requireContext()).skip());
//
//        login.setOnClickListener(v -> {
//
//            if (NetworktManager.isVpnConnected()) {
//
//                Snackbar.make(requireView(), "لطفا vpn خود را غیرفعال نمایید", Snackbar.LENGTH_LONG)
//                        .setTextColor(getResources().getColor(R.color.white)).show();
//
//            } else {
//                ProgressDialog progressDialog = DialogManager.getLoadingDialog(requireContext(), false);
//                if (isEmailLogin) {
//
//                    WebApi.loginUserEmail(Objects.requireNonNull(login_with_email_edittext.getText()).toString(),
//                            Objects.requireNonNull(password.getText()).toString(), new DefaultListener() {
//                                @Override
//                                public void onSuccess(Response<ResponseBody> response) {
//
//                                    progressDialog.dismiss();
//
//                                    if (response.isSuccessful()) {
//
//                                        try {
//
//                                            JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
//                                            if (obj.getBoolean("status")) {
//
//                                                getTokenInformation(obj.getString("user_token"),Objects.requireNonNull(password.getText()).toString());
//
//                                            } else {
//
//                                                Snackbar.make(requireView(), "ایمیل یا پسورد وارد شده اشتباه میباشد", Snackbar.LENGTH_LONG)
//                                                        .setTextColor(getResources().getColor(R.color.white)).show();
//
//                                            }
//
//                                        } catch (JSONException | IOException e) {
//                                            Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                    .setTextColor(getResources().getColor(R.color.white)).show();
//                                        }
//
//                                    } else {
//
//                                        Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                .setTextColor(getResources().getColor(R.color.white)).show();
//
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFailure(Throwable throwable) {
//                                    progressDialog.dismiss();
//                                    Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                            .setTextColor(getResources().getColor(R.color.white)).show();
//
//                                }
//                            });
//
//                } else {
//
//                    WebApi.loginUserNumber(Objects.requireNonNull(login_with_number_edittext.getText()).toString(),
//                            Objects.requireNonNull(password.getText()).toString(), new DefaultListener() {
//                                @Override
//                                public void onSuccess(Response<ResponseBody> response) {
//                                    progressDialog.dismiss();
//                                    if (response.isSuccessful()) {
//
//                                        try {
//
//                                            JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
//                                            if (obj.getBoolean("status")) {
//
//                                                getTokenInformation(obj.getString("user_token"),Objects.requireNonNull(password.getText()).toString());
//
//                                            } else {
//                                                Snackbar.make(requireView(), "شماره یا پسورد وارد شده اشتباه میباشد", Snackbar.LENGTH_LONG)
//                                                        .setTextColor(getResources().getColor(R.color.white)).show();
//                                            }
//
//                                        } catch (JSONException | IOException e) {
//                                            Log.e("err",e.getMessage());
//                                            Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                    .setTextColor(getResources().getColor(R.color.white)).show();
//                                        }
//
//                                    } else {
//                                        Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                .setTextColor(getResources().getColor(R.color.white)).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable throwable) {
//                                    progressDialog.dismiss();
//                                    Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                            .setTextColor(getResources().getColor(R.color.white)).show();
//                                }
//                            });
//
//                }
//            }
//        });
//    }
//
//    private void getTokenInformation(String user_token , String pass) {
//
//        WebApi.getUserInfo(user_token, pass, new DefaultListener() {
//            @Override
//            public void onSuccess(Response<ResponseBody> response) {
//
//                if (response.isSuccessful()) {
//                    try {
//                        JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
//
//                        if (obj.getBoolean("status")) {
//
//                            User.setUserId(obj.getInt("user_id"));
//                            User.setUserStatus(obj.getInt("user_status"));
//                            User.setUserNameFamily(obj.getString("user_namefamily"));
//                            User.setUserEmail(obj.getString("user_email"));
//                            User.setUserNumber(obj.getString("user_number"));
//                            User.setUserToken(user_token);
//                            User.setUserPassword(pass);
//
//                            startActivity(new Intent(requireContext(), MainActivity.class));
//                            requireActivity().finish();
//
//                        } else {
//
//                            Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                    .setTextColor(getResources().getColor(R.color.white)).show();
//
//                        }
//
//                    } catch (JSONException e) {
//                        Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                .setTextColor(getResources().getColor(R.color.white)).show();
//                    } catch (IOException e) {
//                        Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                .setTextColor(getResources().getColor(R.color.white)).show();
//                    }
//
//                } else {
//                    Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                            .setTextColor(getResources().getColor(R.color.white)).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                        .setTextColor(getResources().getColor(R.color.white)).show();
//            }
//        });
//
//    }
//
//    private void findViews(View view) {
//        login_with_number = view.findViewById(R.id.login_with_number);
//        login_with_email = view.findViewById(R.id.login_with_email);
//        login_with_email_edittext = view.findViewById(R.id.login_with_email_edittext);
//        login_with_number_edittext = view.findViewById(R.id.login_with_number_edittext);
//        password = view.findViewById(R.id.password);
//        register = view.findViewById(R.id.register);
//        login = view.findViewById(R.id.login);
//        skip = view.findViewById(R.id.skip);
//    }
}
