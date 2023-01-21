from django.urls import path
from . import views


urlpatterns = [
    path('anomaly_detection', views.anomaly_detection_view),
    path('clustering_sklearn', views.clustering_sklearn_view),
    path('clustering_scratch', views.clustering_scratch_view),
    path('clustering_university', views.clustering_university_view),
    path('clustering_field', views.clustering_field_view),
    path('clustering_workspace', views.clustering_workspace_view),
    path('clustering_uni_work', views.clustering_uni_work),
]