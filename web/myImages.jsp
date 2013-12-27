<%@page import="ims.management.LoginManagement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="ims.management.UserManagement"%>
<%@page import="ims.entity.Image"%>
<%
    LoginManagement lm = new LoginManagement(session);
    lm.userPermissionForThisPage(response, new String[]{"user","contributor"});
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
        <div class="row text-center">
        <% 
            UserManagement um = new UserManagement();
            Set<Image> imagesSet = um.getUserImages(lm.getUser());
            ArrayList<Image> images = new ArrayList<Image>();
            images.addAll(imagesSet);
            for(int i=0; i<images.size(); i++){
                if(i%3==0){
        %>
        </div>
        <div class="row text-center">
            <div class="col-md-3">
                <form class="form-inline" role="form" action="./GetImage" method="POST">
                        
                    <input type="hidden" name="imageId" value="<%=images.get(i).getIdImage() %>"  />
                    <input type="image" width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin"  />
                </form>
            </div>
        <%
                }
                else{
                 %>
           
            <div class="col-md-3 col-md-offset-1">
                <form class="form-inline" role="form" action="./GetImage" method="POST">
                        
                    <input type="hidden" name="imageId" value="<%=images.get(i).getIdImage() %>"  />
                    <input type="image" width="300" height="188" src="GetImage?imageId=<%=images.get(i).getIdImage()%>&size=300"  class="img-rounded tumbnail-margin"  />
                </form>
            </div>
        <%   
                }
            }
        %>
            
        </div>
    </body>
</html>
