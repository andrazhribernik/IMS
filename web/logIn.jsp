<%-- 
    Document   : logIn
    Created on : Dec 27, 2013, 10:49:59 AM
    Author     : andrazhribernik
--%>

<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        <div class="col-md-4 col-md-offset-4">
            <form role="form" method="POST" action="LoginServlet">
                <div class="form-group">
                  <label for="exampleInputEmail1">Email</label>
                  <input type="text" class="form-control" id="exampleInputEmail1" placeholder="i.e. jack@gmail.com" name="username" />
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">Password</label>
                  <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password" />
                </div>
                
                <button type="submit" class="btn btn-default">Login</button>
                <% if(request.getParameter("message") != null){ %>
                    <div class="alert alert-danger"><%=request.getParameter("message") %></div>
                <% } %>
                
            </form>
            <br>    
            <div>
                Or <a href="./register.jsp">Sign up</a> for new account.
            </div>   
            <div>
                Or <a href="./resetPasswordForm.jsp">get</a> a new password.      
            </div>
        </div>
    </body>
</html>
