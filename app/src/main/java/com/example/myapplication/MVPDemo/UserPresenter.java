package com.example.myapplication.MVPDemo;

public class UserPresenter {
    private UserView mUserView;
    private UserModel mUserModel;

    public UserPresenter(UserView view) {
        mUserView = view;
        mUserModel = new UserModelImp();
    }

    public void saveUser( int id, String name) {
        mUserModel.setID(id);
        mUserModel.setName(name);
    }
    public void loadUser( int id) {
//
        UserBean user = mUserModel.load(id);
//这里通过setview的方式改变view，在MVVM中这一步通过date binding完成
        mUserView.setName(user.getName()); // 通过调用IUserView的方法来更新显示
    }
}
