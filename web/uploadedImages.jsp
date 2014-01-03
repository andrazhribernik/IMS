<%@page import="ims.management.ImageManagement"%>
<%@page import="ims.management.LoginManagement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="ims.management.UserManagement"%>
<%@page import="ims.entity.Image"%>
<%
    LoginManagement lm = new LoginManagement(session);
    lm.userPermissionForThisPage(response, new String[]{"contributor"});
%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <jsp:include page="templates/header.jsp" />
    <body>
        <jsp:include page="templates/navigationBar.jsp" />
        
        <% 
            UserManagement um = new UserManagement();
            Set<Image> imagesSet = um.getUserUploadedImages(lm.getUser());
            ArrayList<Image> images = new ArrayList<Image>();
            images.addAll(imagesSet);
            for(int i=0; i<images.size(); i++){
                
        %>
        <div class="row text-center tumbnail-margin">
            <div class="col-md-3 col-md-offset-2">
                <img src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300" class="img-rounded"> 
            </div>
            <div class="col-md-4 col-md-offset-1">
                <% 
                    ImageManagement im = new ImageManagement();
                    Integer soldNumber = im.soldHowManyTimes(images.get(i));
                %>
                <div class="panel panel-default">
                    <div class="panel-heading">
                      <h3 class="panel-title"><%=images.get(i).getName()%>
                      <% 
                        if(soldNumber == 0){
                      %>
                        <span class="label label-danger align-right">Unsold</span>
                      <% } else{ %>
                        <span class="align-right label label-success">Sold</span>
                      <% } %>
                      </h3>
                    </div>
                    <div class="panel-body">
                        <dl class="dl-horizontal my-width">
                            <dt>Price</dt>
                            <dd>&euro; <%=images.get(i).getPriceD()%></dd>
                            <dt>Items sold</dt>
                            <dd><%=soldNumber %></dd>
                        </dl>
                    </div>
                    <div class="panel-footer">
                        <dl class="dl-horizontal my-width">
                            <dt>Income</dt>
                            <dd>&euro; <%=images.get(i).getPriceD()*soldNumber %></dd>
                        </dl>
                    </div>    
                </div>
            </div>
        </div>
        <%   
               
            }
        %>
            
        
    </body>
</html>
