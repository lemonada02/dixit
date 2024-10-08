<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="gameDetails">

    <h2>Game Data</h2>

    <table class="table table-striped">
        <tr>
            <th>Id</th>
            <td><c:out value="${game.id}"/></td>
        </tr>
        <tr>
            <th>Number of Players</th>
            <td><c:out value="${game.numberOfPlayers}/4"/></td>
        </tr>
    </table>
    <br/>
    <br/>
    <br/>
    <h2>Players</h2>
    <table class="table table-striped">
        <c:forEach var="scoreboard" items="${scoreboards}">
            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt>Username</dt>
                        <dd><c:out value="${scoreboard.user.username}"/></dd>
                    </dl>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div style ="text-align: center">
    	<c:if test="${game.numberOfPlayers > 1}">
    		<a style ="font-size: 46px; color: #2f324f;" href="<spring:url value="/">"> Play!</a>
		</c:if>    
    </div>
    
</petclinic:layout>