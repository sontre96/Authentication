
import java.util.List;
import java.util.Scanner;

public class Controller {

    String username;
    String password;
    Repository repository = new Repository();
    List<User> users = repository.getData();

    Scanner scanner = new Scanner(System.in);

    public void Home(){
        Menu.mainMenu();
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose){
            case 1:
                //Đăng nhập
                login();
                break;
            case 2:
                //Đăng ký
                createNewAccount();
                break;
            default:
                break;
        }
    }

    public void login(){
        boolean checklogin = false;
        while (!checklogin){
            System.out.println("Nhập tên đăng nhập: ");
            username = scanner.nextLine();
            int count =0;
            for (int i =0; i < users.size(); i++) {
                if (username.equals(users.get(i).getUsername())) {
                    count++;
                    System.out.println("Nhập mật khẩu: ");
                    //nhập mật khẩu
                    password = scanner.nextLine();
                    if (password.equals(users.get(i).getPassword())) {
                        loginSuccess();
                        checklogin = true;
                    } else {
                        loginFail();
                        break;
                    }
                }
            } if (count == 0){
                System.out.println("Kiểm tra lại username");
            }
        }

    }

    public void loginSuccess(){
        System.out.println("Chào mừng" + username + "\n Bạn có thể thực hiện các công việc sau:");
        Menu.loginSuccess();
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose){
            case 1:
                //Thay đổi username
                changUsername();
                break;
            case 2:
                changeEmail();
                break;
            case 3:
                changePassword();
                break;
            case 4:
                Home();
                break;
            case 0:
                System.out.println("Tạm biệt!!!!");
                System.exit(1);
                break;
            default:
                break;
        }
    }

    public void changePassword(){
        //Thay đổi mật khẩu
        System.out.println("Nhập mật khẩu cũ: ");
        password = scanner.nextLine();
        int count = 0;
        for (int i = 0; i < users.size(); i++){
            if (password.equals(users.get(i).getPassword()) && username.equals(users.get(i).getUsername())) {
                count ++;
                boolean check = false;
                String newPassword = null;
                while (!check){
                    try {
                        System.out.println("Nhập mật khẩu mới: ");
                        newPassword = Validate.vaildatePassword(scanner.nextLine());
                        check = true;
                    }catch (RuntimeException e){
                        System.out.println(e);
                    }
                }
                users.get(i).setPassword(newPassword);
                System.out.println("Thay đổi mật khẩu thành công, vui lòng đăng nhập lại");
                login();
                password = newPassword;
            }
        }
        if (count == 0){
            System.out.println("Bạn vừa nhập sai mật khẩu");
        }
    }

    public void changUsername(){
        //Thay đổi Username
        boolean check = false;
        while (!check) {
            try {
                System.out.println("Nhập username mới");
                String newUsername = scanner.nextLine();
                for (int i = 0; i < users.size(); i++){
                    if (username.equals(users.get(i).getUsername())){
                        if (!newUsername.equals(users.get(i).getUsername())) {
                            users.get(i).setUsername(newUsername);
                            username = newUsername;
                            System.out.println("Thay đổi username thành công");
                            check = true;
                        }else {
                            throw new RuntimeException("Username đã tồn tại");
                        }
                    }
                }
            }catch (RuntimeException e) {
                System.out.println(e.getMessage() + ", vui lòng nhập lại username");

            }
        }loginSuccess();
    }

    public void changeEmail() {
        boolean check = false;
        String newEmail = null;
        while (!check){
            try {
                System.out.println("Nhập email mới: ");
                newEmail = Validate.vaildateEmail(scanner.nextLine());
                for (int i = 0; i < users.size(); i++) {
                    if (username.equals(users.get(i).getUsername())){
                        if (!newEmail.equals(users.get(i).getEmail())){
                            users.get(i).setEmail(newEmail);
                            System.out.println("Thay đổi email thành công");
                        }else {
                            throw new RuntimeException("Email đã tồn tại");
                        }
                    }
                }
                check = true;
            }catch (RuntimeException e){
                System.out.println(e.getMessage() + ", vui lòng nhập lại email");
            }
        }
        loginSuccess();
    }

    public void loginFail(){
        Menu.LoginFail();
        int choose = scanner.nextInt();
        scanner.nextLine();
        switch (choose){
            case 1:
                System.out.println("Đăng nhập lại:");
                login();
                break;
            case 2:
                forgotPassword();
                break;
        }
    }

    public void forgotPassword(){
        System.out.println("Vui lòng nhập email của bạn");
        String email = scanner.nextLine();
        int count = 0;
        for (int i = 0; i < users.size(); i++) {
            if (email.equals(users.get(i).getEmail())){
                count++;
                boolean check = false;
                while (!check) {
                    try {
                        System.out.println("Nhập mật khẩu mới");
                        String newPassword = Validate.vaildatePassword(scanner.nextLine());
                        users.get(i).setPassword(newPassword);
                        System.out.println("Đổi mật khẩu thành công, tiến hành đăng nhập lại.");
                        check =true;
                        login();
                    }catch (RuntimeException e){
                        // TODO: handle exception
                        System.out.println(e.getMessage() + ", vui lòng nhập lại.");
                    }
                }
            }
        }if (count == 0){
            System.out.println("Email không tồn tại, hãy nhập lại email");
            forgotPassword();
        }
    }

    public void createNewAccount(){
        boolean check = false;
        long id = 0;
        String newUsername = null;
        String newEmail = null;
        String newPassword = null;
        while (!check){
            try {
                id = users.size() + 1;
                System.out.println("Nhập username: ");
                newUsername = scanner.nextLine();
                System.out.println("Nhập Email: ");
                newEmail = Validate.vaildateEmail(scanner.nextLine());
                System.out.println("Nhập password");
                newPassword = Validate.vaildatePassword(scanner.nextLine());
                for (int i = 0; i < users.size(); i++){
                    if(newUsername.equals(users.get(i).getUsername())) throw new RuntimeException("Username đã tồn tại");
                    if(newEmail.equals(users.get(i).getEmail())) throw new RuntimeException("Username đã tồn tại");
                }
                check = true;
            }catch (RuntimeException e){
                System.out.println(e.getMessage() + ", vui lòng thực hiên lại");
            }
        }
        users.add(new User(id, newUsername, newEmail, newPassword));
        System.out.println("Dăng ký thành công");
        System.out.println("Tiến hành đăng nhập");
        login();
    }
}
