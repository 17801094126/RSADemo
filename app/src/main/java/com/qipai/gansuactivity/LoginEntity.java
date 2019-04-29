package com.qipai.gansuactivity;

public class LoginEntity {

    private String retcode;
    private int retmsg;
    private Data data;

    @Override
    public String toString() {
        return "LoginEntity{" +
                "retcode='" + retcode + '\'' +
                ", retmsg=" + retmsg +
                ", data=" + data +
                '}';
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public int getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(int retmsg) {
        this.retmsg = retmsg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    class Data{
        private String userAlias;
        private int roleId;
        private int loginStatus;
        private String sign;

        public String getUserAlias() {
            return userAlias;
        }

        public void setUserAlias(String userAlias) {
            this.userAlias = userAlias;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public int getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(int loginStatus) {
            this.loginStatus = loginStatus;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "userAlias='" + userAlias + '\'' +
                    ", roleId=" + roleId +
                    ", loginStatus=" + loginStatus +
                    ", sign='" + sign + '\'' +
                    '}';
        }
    }
}
