
from django.contrib import admin
from django.urls import include, path

# urls
urlpatterns = [
    path('api/', include('users.urls')),
]