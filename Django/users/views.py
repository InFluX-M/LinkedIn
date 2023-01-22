from django.http.response import JsonResponse

from rest_framework.decorators import api_view
import users.anomaly_detection as ad
import users.clustering_recommender as cr
from users.models import user

@api_view(['GET'])
def anomaly_detection_view(request):
    if request.method == 'GET':
        data = ad.getDataAnomalyDetection()
        return JsonResponse(list(data), safe=False)


@api_view(['GET'])
def clustering_sklearn_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_scratch(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_scratch_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_scratch(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_university_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_university(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_workspace_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_workspace(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_specialties_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_specialties(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_field_view(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_field(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


@api_view(['GET'])
def clustering_uni_work(request):
    if request.method == 'GET':
        X, df = cr.preprocessing()
        data = cr.clustering_uni_work(X, df)

        ls = list()
        for key in data:
            cluster = list()
            for idx in data[key]:
                cluster.append(idx)
            ls.append(cluster)

        return JsonResponse(ls, safe=False)


from users.mysql import show
@api_view(['GET'])

def show1(request):
    data = show()
    print(data)
    return JsonResponse(data, safe=False)
