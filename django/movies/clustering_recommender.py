import numpy as np
import pandas as pd
from sklearn.cluster import KMeans


def preprocessing():
    df = pd.read_json("/media/influx/Programming/Projects/project-final-random/Linkedin/src/main/resources/users.json")
    df_train = df.drop(['dateOfBirth', 'name', 'email', 'profile_pic', 'connectionId'], axis=1)

    df_train = pd.get_dummies(df_train, columns=["field"])

    df_train = pd.get_dummies(df_train, columns=["universityLocation"])

    df_train = pd.get_dummies(df_train, columns=["workplace"])

    spec = set(s for user in df_train['specialties'] for s in user)

    for special in spec:
        df_train[special] = [0 for i in range(2000)]

    i = 0
    for user in df_train['specialties']:
        for s in user:
            df_train.at[i, s] = 1
        i += 1

    df_train = df_train.drop(['specialties', 'id'], axis=1)

    X = df_train.values

    return X, df


def clustering_sklearn(X, df):
    kmeans = KMeans(n_clusters=100, init='k-means++', random_state=10)
    y_kmeans = kmeans.fit_predict(X)

    idx = 0
    clusters_sklearn = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_sklearn:
            clusters_sklearn[cluster_x_i] = list()
        clusters_sklearn[cluster_x_i].append(idx)
        idx += 1

    user_clusters_sklearn = dict()

    for key_cluster in clusters_sklearn:
        cluster = clusters_sklearn[key_cluster]
        for id_xi in cluster:
            if key_cluster not in user_clusters_sklearn:
                user_clusters_sklearn[key_cluster] = list()
            user_clusters_sklearn[key_cluster].append(df.iloc[id_xi, :])

    return user_clusters_sklearn


"""
Implement K-Means Clustering Algorithm from scratch
"""

def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx

def compute_centroids(X, idx, K):
    m, n = X.shape

    centroids = np.zeros((K, n))

    for k in range(K):
        points = X[idx == k]
        centroids[k] = np.mean(points, axis=0)

    return centroids

def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        cost += pow(np.linalg.norm(X[i] - centroids[idx[i]]), 2)
    return 1 / m * cost

def run_kMeans(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids
    idx = np.zeros(m)
    cost = 0

    i = 1
    while True:

        idx = find_closest_centroids(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i += 1

def kMeans_init_centroids(X, K):
    randidx = np.random.permutation(X.shape[0])
    centroids = X[randidx[:K]]
    return centroids

def clustering_scratch(X, df):
    K = 80
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_scratch = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_scratch:
            clusters_scratch[cluster_x_i] = list()
        clusters_scratch[cluster_x_i].append(idx)
        idx += 1

    return clusters_scratch


"""
Implement K-Means Clustering Algorithm By increasing the impact of the University 
"""


def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(4, 17):
                norm_ij += 2 * abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx


def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(4, 17):
            norm_ij += 2 * abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)

    return 1 / m * cost


def clustering_university(X, df):
    K = 40
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_university = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_university:
            clusters_university[cluster_x_i] = list()
        clusters_university[cluster_x_i].append(idx)
        idx += 1

    return clusters_university


"""
Implement K-Means Clustering Algorithm By increasing the impact of the University 
"""

def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(17, 32):
                norm_ij += 2 * abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx

def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(17, 32):
            norm_ij += 2 * abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)

    return 1 / m * cost



def clustering_workspace(X, df):
    K = 40
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_workspace = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_workspace:
            clusters_workspace[cluster_x_i] = list()
        clusters_workspace[cluster_x_i].append(idx)
        idx += 1

    return clusters_workspace


"""
Implement K-Means Clustering Algorithm By increasing the impact of the specialties 
"""

def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(32, 55):
                norm_ij += 2 * abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx


def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(32, 55):
            norm_ij += 2 * abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)

    return 1 / m * cost

def clustering_specialties(X, df):

    K = 40
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_specialties = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_specialties:
            clusters_specialties[cluster_x_i] = list()
        clusters_specialties[cluster_x_i].append(idx)
        idx += 1

    return clusters_specialties


"""
Implement K-Means Clustering Algorithm By increasing the impact of the fields 
"""

def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(0, 4):
                norm_ij += 3 * abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx


def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(0, 4):
            norm_ij += 3 * abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)

    return 1 / m * cost


def clustering_field(X, df):
    K = 10
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_field = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_field:
            clusters_field[cluster_x_i] = list()
        clusters_field[cluster_x_i].append(idx)
        idx += 1

    return clusters_field


"""
Implement K-Means Clustering Algorithm By increasing the impact of the workplace & University
"""

def find_closest_centroids(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(4, 32):
                norm_ij += 3 * abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)

    return idx


def cost_function(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(4, 32):
            norm_ij += 3 * abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)

    return 1 / m * cost


def clustering_uni_work(X, df):

    K = 70
    centroids, y_kmeans, min_cost = run_kMeans(X, K)

    idx = 0
    clusters_uni_work = dict()

    for cluster_x_i in y_kmeans:
        if cluster_x_i not in clusters_uni_work:
            clusters_uni_work[cluster_x_i] = list()
        clusters_uni_work[cluster_x_i].append(idx)
        idx += 1


    return clusters_uni_work