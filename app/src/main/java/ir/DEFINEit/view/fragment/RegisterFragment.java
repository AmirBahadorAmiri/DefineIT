/*
 *  Copyright (c) 2022
 *  Coded by Bahador Amiri ** JotaByte **
 *  at 7/8/22, 5:58 PM
 *  email : abadan918@gmail.com
 */

package ir.DEFINEit.view.fragment;

import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {

//    AppCompatEditText register_user_namefamily, register_user_email, register_user_number, register_user_password;
//    AppCompatButton register;
//    AppCompatTextView login, skip;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.register_fragment, container, false);
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
//        login.setOnClickListener(v -> ((RegisterActivity) requireContext()).loadFragment(1));
//
//        skip.setOnClickListener(v -> ((RegisterActivity) requireContext()).skip());
//
//        register.setOnClickListener(v -> {
//
//            if (NetworktManager.isVpnConnected()) {
//                Snackbar.make(register, "لطفا vpn خود را غیرفعال نمایید", Snackbar.LENGTH_LONG)
//                        .setTextColor(getResources().getColor(R.color.white)).show();
//            } else {
//                ProgressDialog progressDialog = DialogManager.getLoadingDialog(requireContext(), false);
//
//                WebApi.createUserAccount(Objects.requireNonNull(register_user_email.getText()).toString(),
//                        Objects.requireNonNull(register_user_number.getText()).toString(),
//                        Objects.requireNonNull(register_user_namefamily.getText()).toString(),
//                        Objects.requireNonNull(register_user_password.getText()).toString(),
//                        new DefaultListener() {
//                            @Override
//                            public void onSuccess(Response<ResponseBody> response) {
//                                progressDialog.dismiss();
//                                if (response.isSuccessful()) {
//                                    try {
//                                        JSONObject obj = new JSONObject(Objects.requireNonNull(response.body()).string());
//                                        if (obj.getBoolean("status")) {
//
//                                            if (obj.getInt("return") == StaticDatas.R_USER_AVAILABLED) {
//                                                Snackbar.make(requireView(), "ایمیل یا شماره وارد شده قبلا استفاده شده", Snackbar.LENGTH_LONG)
//                                                        .setTextColor(getResources().getColor(R.color.white)).show();
//                                            } else {
//                                                getTokenInformation(obj.getString("user_token"),Objects.requireNonNull(register_user_password.getText()).toString());
//                                            }
//
//                                        } else {
//                                            Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                    .setTextColor(getResources().getColor(R.color.white)).show();
//                                        }
//
//
//                                    } catch (IOException | JSONException e ) {
//                                        Log.e("toka2",e.getMessage());
//                                        Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                                .setTextColor(getResources().getColor(R.color.white)).show();
//                                    }
//
//                                } else {
//                                    Log.e("toka2","response is false");
//                                    Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                            .setTextColor(getResources().getColor(R.color.white)).show();
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Throwable throwable) {
//                                Log.e("toka2",throwable.getMessage());
//                                progressDialog.dismiss();
//                                Snackbar.make(requireView(), "متاسفانه در ارتباطی با سرور مشکلی پیش آمد", Snackbar.LENGTH_LONG)
//                                        .setTextColor(getResources().getColor(R.color.white)).show();
//                            }
//                        });
//            }
//
//        });
//
//    }
//
//    private void getTokenInformation(String user_token, String pass) {
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
//        register_user_namefamily = view.findViewById(R.id.register_user_namefamily);
//        register_user_email = view.findViewById(R.id.register_user_email);
//        register_user_number = view.findViewById(R.id.register_user_number);
//        register_user_password = view.findViewById(R.id.register_user_password);
//        register = view.findViewById(R.id.register);
//        login = view.findViewById(R.id.login);
//        skip = view.findViewById(R.id.skip);
//    }
}
