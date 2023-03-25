<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categoria App</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
</head>
<body>
<header>
		<nav class="navbar navbar-expand-md navbar-dark bg-dark">
			<div>
				<a href="" class="navbar-brand"> Categoria App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Categorias</a></li>
			</ul>
		</nav>
	</header>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${categoria != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${categoria == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${categoria != null}">
            			Editar categoria
            		</c:if>
						<c:if test="${categoria == null}">
            			Añadir categoria
            		</c:if>
					</h2>
				</caption>

				<c:if test="${categoria != null}">
					<input type="hidden" name="id" value="<c:out value='${categoria.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Nombre de categoria</label> <input type="text"
						value="<c:out value='${categoria.nombre}' />" class="form-control"
						name="nombre" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Descripción de categoria</label> <input type="text"
						value="<c:out value='${categoria.descripcion}' />" class="form-control"
						name="descripcion">
				</fieldset>

				<button type="submit" class="btn btn-success">Aceptar</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>