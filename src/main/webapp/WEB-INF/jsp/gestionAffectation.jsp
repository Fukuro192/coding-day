<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">

<head>
<!-- Required meta tags-->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Coding day">
<meta name="author" content="Salah EL MESBAHI">
<meta name="keywords" content="Colorlib Templates CodingDay">

<!-- Title Page-->
<title>Gestion Des Comptes - Gestion d'Affectation</title>

<!-- Icons font CSS-->
<link href="/vendor/mdi-font/css/material-design-iconic-font.min.css"
	rel="stylesheet" media="all">
<link href="/vendor/font-awesome-4.7/css/font-awesome.min.css"
	rel="stylesheet" media="all">
<!-- Font special for pages-->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i"
	rel="stylesheet">

<!-- Vendor CSS-->
<link href="/vendor/select2/select2.min.css" rel="stylesheet"
	media="all">
<link href="/vendor/datepicker/daterangepicker.css" rel="stylesheet"
	media="all">

<!-- Main CSS-->
<link href="/css/bootstrap.css" rel="stylesheet" media="all">
<link href="/css/bootstrap.min.css" rel="stylesheet" media="all">
<link href="/css/main.css" rel="stylesheet" media="all">
<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333;
}

li {
	float: left;
}

li a {
	display: block;
	color: white;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
}

li a:hover {
	background-color: #111;
}
</style>
</head>

<body>

	<ul>
		<li><a href="/">Identification</a></li>
		<li><a href="/gestioncompte">Gestion du compte</a></li>
		<li><a href="/gestionAffectation">Gestion d'affectation</a></li>
		<li><a href="/gestionadresses">Gestion d'adresses</a></li>
		<li><a href="/logout">Se deconnecter</a></li>
	</ul>
	
	<div class="page-wrapper bg-blue p-t-100 p-b-100 font-robo">
		<div class="wrapper wrapper--w680">
			<div class="card card-1">
				<div class="card-heading"></div>
				<script type="text/javascript">
					function hide(id) {
						document.getElementById(id).style.display = "none";
					}
					function show(id) {
						var list = [ "add", "adresses_personne",
								"adresses_non_personne" ];
						list.forEach(hide);
						document.getElementById(id).style.display = "block";
					}
				</script>
				<c:if test="${not empty error}">
					<div style="margin-top: 40px" class="alert alert-danger"
						role="alert">${error}</div>
				</c:if>
				<center>
					<div class="p-t-20">
						<button class="btn btn--radius btn--green" onclick="show('add')"
							type="button">Ajouter une Adresse</button>
						<button class="btn btn--radius btn--green"
							onclick="show('adresses_personne')" type="button">Lister
							mes Adresses</button>
						<button class="btn btn--radius btn--green"
							onclick="show('adresses_non_personne')" type="button">Lister
							les autres Adresses</button>
					</div>
				</center>
				<div class="card-body" id="add" style="display: none;">
					<h2 class="title">Gestion d'affectation</h2>
					<form action="/addadresse" method="POST">
						<div class="input-group">
							<input class="input--style-1" type="text" placeholder="RUE"
								name="rue" value="${rue}" pattern="[A-Z].*" title="La rue doit commencer par un majuscule">
						</div>
						<div class="input-group">
							<input class="input--style-1" type="text"
								placeholder="CODE POSTALE" name="code_postal"
								value="${code_postal }" pattern="[0-9]{5}" title="Le Code postal doit contenir exactement 5 chiffres">
						</div>
						<div class="input-group">
							<input class="input--style-1" type="text" placeholder="VILLE"
								name="ville" value="${ville}" pattern="[A-Z][a-z ]*" title="La ville doit commencer par un majuscule">
						</div>
						<center>
							<div class="p-t-20">
								<button class="btn btn--radius btn--green" type="submit">Ajouter
									Adresse</button>
							</div>
						</center>


					</form>
				</div>
				<div class="card-body" id="adresses_personne"
					style="display: block;">
					<h2 class="title">Liste de mes adresses</h2>
					<c:choose>
						<c:when test="${not empty adresses_personne }">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>Rue</th>
										<th>Code Postal</th>
										<th>Ville</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="adresse" items="${adresses_personne}">
										<tr>
											<td>${adresse.rue }</td>
											<td>${adresse.code_postal }</td>
											<td>${adresse.ville }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<p>Vous n'avez pas d'adresse enregistrée</p>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="card-body" id="adresses_non_personne"
					style="display: none;">
					<h2 class="title">Liste des adresses qui ne m'appartiennent
						pas</h2>
					<c:choose>
						<c:when test="${not empty adresses_non_personne }">
							<table class="table table-striped" style="margin-left: -7%;">
								<thead>
									<tr>
										<th><button type="button" class="btn btn-default" style="color:black">Rue</button></th>
										<th><button type="button" class="btn btn-default" style="color:black">Code Postal</button></th>
										<th><button type="button" class="btn btn-default" style="color:black">Ville</button></th>
										<th><button type="button" class="btn btn-default" style="color:black">Proprietaire</button></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="adresse" items="${adresses_non_personne}">
										<tr>
											<td>${adresse.rue }</td>
											<td>${adresse.code_postal }</td>
											<td>${adresse.ville }</td>
											<td>${adresse.personne.nom }</td>
											<td><a href="/affecter/${adresse.id }">Affecter</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<p>La liste est vide</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>

	<c:if test="${not empty error}">
		<script type="text/javascript">
			show('add');
		</script>
	</c:if>

	<!-- Jquery JS-->
	<script src="/vendor/jquery/jquery.min.js"></script>
	<!-- Vendor JS-->
	<script src="/vendor/select2/select2.min.js"></script>
	<script src="/vendor/datepicker/moment.min.js"></script>
	<script src="/vendor/datepicker/daterangepicker.js"></script>

	<!-- Main JS-->
	<script src="/js/global.js"></script>

</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->
