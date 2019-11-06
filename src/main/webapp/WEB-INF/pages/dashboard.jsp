<!--

=========================================================
* Argon Dashboard - v1.1.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard
* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/argon-dashboard/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>
    Your screenings
  </title>
  <!-- Favicon -->
  <link href="./assets/img/brand/favicon.png" rel="icon" type="image/png">
  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
  <!-- Icons -->
  <link href="./assets/js/plugins/nucleo/css/nucleo.css" rel="stylesheet" />
  <link href="./assets/js/plugins/@fortawesome/fontawesome-free/css/all.min.css" rel="stylesheet" />
  <!-- CSS Files -->
  <link href="./assets/css/argon-dashboard.css?v=1.1.0" rel="stylesheet" />
  <!-- MDBootstrap Datatables  -->
  <link href="./assets/css/addons/datatables.min.css" rel="stylesheet">
</head>

<body class="">
<!-- Screening Modal -->
<div class="modal fade" id="newScreeningModal" tabindex="-1" role="dialog" aria-labelledby="newScreeningModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="screeningModalLabel">New Screening</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="./manageScreening">
          <div class="form-group">
            <label class="form-control-label">Movie</label>
            <input type="text" class="form-control" name="movie_title">
          </div>
          <div class="form-group">
            <label class="form-control-label">Cinema</label>
            <input type="text" class="form-control" name="cinema_name">
          </div>
          <div class="form-group">
            <label class="form-control-label">Screening Time</label>
            <textarea class="form-control" name="screening_time"></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label">Room</label>
            <textarea class="form-control" name="room"></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label">Property</label>
            <textarea class="form-control" name="property"></textarea>
          </div>
          <input type="hidden" name="user_id" value="${user.userId}"/>
          <input type="hidden" name="operation_post" value="create"/>
          <input type="submit" value="Create" class="btn btn-primary"/>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Movie Modal -->
<div class="modal fade" id="newMovieModal" tabindex="-1" role="dialog" aria-labelledby="newMovieModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="movieModalLabel">New Movie</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="./manageMovie">
          <div class="form-group">
            <label class="form-control-label">Title</label>
            <input type="text" class="form-control" name="title">
          </div>
          <div class="form-group">
            <label class="form-control-label">Release Year</label>
            <input type="text" class="form-control" name="release_year">
          </div>
          <div class="form-group">
            <label class="form-control-label">Category</label>
            <input type="text" class="form-control" name="category">
          </div>
          <input type="hidden" name="operation_post" value="create"/>
          <input type="submit" value="Create" class="btn btn-primary"/>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Cinema Modal -->
<div class="modal fade" id="newCinemaModal" tabindex="-1" role="dialog" aria-labelledby="newCinemaModal" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="cinemaModalLabel">New Cinema</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form method="post" action="./manageCinema">
          <div class="form-group">
            <label class="form-control-label">Name</label>
            <input type="text" class="form-control" name="name">
          </div>
          <div class="form-group">
            <label class="form-control-label">City</label>
            <input type="text" class="form-control" name="city">
          </div>
          <div class="form-group">
            <label class="form-control-label">Price</label>
            <input type="text" class="form-control" name="price">
          </div>
          <input type="hidden" name="operation_post" value="create"/>
          <input type="submit" value="Create" class="btn btn-primary"/>
        </form>
      </div>
    </div>
  </div>
</div>

  <nav class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white" id="sidenav-main">
    <div class="container-fluid">
      <!-- Toggler -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <!-- Brand -->
      <a class="navbar-brand pt-0">
        <img src="./assets/img/brand/blue.png" class="navbar-brand-img" alt="..">
      </a>
      <!-- User -->
      <ul class="nav align-items-center d-md-none">
        <li class="nav-item dropdown">
          <a class="nav-link nav-link-icon" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="ni ni-bell-55"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right" aria-labelledby="navbar-default_dropdown_1">
            <a class="dropdown-item" href="#">Action</a>
            <a class="dropdown-item" href="#">Another action</a>
            <div class="dropdown-divider"></div>
            <a class="dropdown-item" href="#">Something else here</a>
          </div>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <div class="media align-items-center">
              <span class="avatar avatar-sm rounded-circle">
                <img alt="Image placeholder" src="./assets/img/theme/team-1-800x800.jpg
">
              </span>
            </div>
          </a>
          <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
            <div class=" dropdown-header noti-title">
              <h6 class="text-overflow m-0">Welcome!</h6>
            </div>
            <a href="./logout" class="dropdown-item">
              <i class="ni ni-user-run"></i>
              <span>Logout</span>
            </a>
          </div>
        </li>
      </ul>
      <!-- Collapse -->
      <div class="collapse navbar-collapse" id="sidenav-collapse-main">
        <!-- Collapse header -->
        <div class="navbar-collapse-header d-md-none">
          <div class="row">
            <div class="col-6 collapse-brand">
              <a>
                <img src="./assets/img/theme/sketch.jpg">
              </a>
            </div>
            <div class="col-6 collapse-close">
              <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#sidenav-collapse-main" aria-controls="sidenav-main" aria-expanded="false" aria-label="Toggle sidenav">
                <span></span>
                <span></span>
              </button>
            </div>
          </div>
        </div>
        <!-- Navigation -->
        <ul class="navbar-nav">
          <li class="nav-item"  class="active" >
            <a class=" nav-link "> <i class="ni ni-tv-2 text-primary"></i> Dashboard</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="main-content">
    <!-- Navbar -->
    <nav class="navbar navbar-top navbar-expand-md navbar-dark" id="navbar-main">
      <div class="container-fluid">
        <!-- Button trigger modal -->
        <table style="width: 30%">
          <tr>
            <td>
              <button type="button" class="btn btn-info" data-toggle="modal" data-target="#newScreeningModal">
                New Screening
              </button>
            </td>
            <td>
              <button type="button" class="btn btn-info" data-toggle="modal" data-target="#newMovieModal">
                New Movie
              </button>
            </td>
            <td>
              <button type="button" class="btn btn-info" data-toggle="modal" data-target="#newCinemaModal">
                New Cinema
              </button>
            </td>
          </tr>
        </table>
        <!-- User -->
        <ul class="navbar-nav align-items-center d-none d-md-flex">
          <li class="nav-item dropdown">
            <a class="nav-link pr-0" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              <div class="media align-items-center">
                <span class="avatar avatar-sm rounded-circle">
                  <img alt="Image placeholder" src="./assets/img/theme/sketch.jpg">
                </span>
                <div class="media-body ml-2 d-none d-lg-block">
                  <span class="mb-0 text-sm  font-weight-bold">${user.username}</span>
                </div>
              </div>
            </a>
            <div class="dropdown-menu dropdown-menu-arrow dropdown-menu-right">
              <div class=" dropdown-header noti-title">
                <h6 class="text-overflow m-0">Welcome!</h6>
              </div>
              <a href="./logout" class="dropdown-item">
                <i class="ni ni-user-run"></i>
                <span>Logout</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
    <!-- End Navbar -->
    <!-- Header -->
    <div class="header bg-gradient-primary pb-8 pt-5 pt-md-8">
      <div class="container-fluid">
        <div class="header-body">
          <!-- Card stats -->
          <div class="row">
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid mt--7">
      <!-- Dark table -->
      <div class="row mt-5">
        <div class="col">
          <div class="card bg-default shadow">
            <div class="card-header bg-transparent border-0">
              <h3 class="text-white mb-0">Showings</h3>
            </div>
            <div class="table-responsive">
              <table class="table align-items-center table-dark table-flush" cellspacing="0" width="100%">
                <thead class="thead-dark">
                  <tr>
                    <th scope="col">Movie</th>
                    <th scope="col">Cinema</th>
                    <th scope="col">Time</th>
                    <th scope="col">Manage</th>
                  </tr>
                </thead>
                <tbody>
                  <C:forEach items="${screenings}" var="screening">
                    <tr>
                      <td>
                        <a href="./manageMovie?operation_get=detail&movie_id=${screening.movie.movieId}">${screening.movie.title}</a>
                      </td>
                      <td>
                        <a href="./manageCinema?operation_get=detail&cinema_id=${screening.cinema.cinemaId}">${screening.cinema.name}</a>
                      </td>
                      <td>
                          ${screening.time}
                      </td>
                      <td>
                        <a href="./manageScreening?operation_get=detail&screening_id=${screening.screeningId}">Details</a>
                        <a href="./manageScreening?operation_get=delete&screening_id=${screening.screeningId}">Delete</a>
                      </td>
                    </tr>
                  </C:forEach>
                </tbody>
              </table>
            </div>
            <div class="py-4 table-dark">
              <nav aria-label="...">
                <ul class="pagination justify-content-end mb-0">
                  <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">
                      <i class="fas fa-angle-left"></i>
                      <span class="sr-only">Previous</span>
                    </a>
                  </li>
                  <li class="page-item active">
                    <a class="page-link" href="#">1</a>
                  </li>
                  <li class="page-item">
                    <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                  </li>
                  <li class="page-item"><a class="page-link" href="#">3</a></li>
                  <li class="page-item">
                    <a class="page-link" href="#">
                      <i class="fas fa-angle-right"></i>
                      <span class="sr-only">Next</span>
                    </a>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--   Core   -->
  <script src="./assets/js/plugins/jquery/dist/jquery.min.js"></script>
  <script src="./assets/js/plugins/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
  <!--   Optional JS   -->
  <!--   Argon JS   -->
  <script src="./assets/js/argon-dashboard.min.js?v=1.1.0"></script>
</body>

</html>