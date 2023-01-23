import numpy as np
import seaborn as sn
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.cluster import KMeans

df = pd.read_json("/media/influx/Programming/Projects/project-final-random/Linkedin/src/main/resources/users.json")

def information_user_cluster(y_kmeans):

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


def kMeans_init_centroids(X, K):
    randidx = np.random.permutation(X.shape[0])
    centroids = X[randidx[:K]]
    return centroids


def compute_centroids(X, idx, K):
    m, n = X.shape
    
    centroids = np.zeros((K, n))
    
    for k in range(K):
        points = X[idx == k]    
        centroids[k] = np.mean(points, axis = 0)
    
    return centroids


def find_closest_centroids_scratch(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_scratch(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        cost += pow(np.linalg.norm(X[i] - centroids[idx[i]]), 2)
    return 1/m * cost

def kmeans_fit_scratch(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_scratch(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_scratch(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1


def find_closest_centroids_university(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(4, 17):
                norm_ij += 2*abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_university(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(4, 17):
                norm_ij += abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)
        
    return 1/m * cost

def kmeans_fit_university(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_university(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_university(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1
     
        
def find_closest_centroids_workplace(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(17, 32):
                norm_ij += abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_workplace(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(17, 32):
            norm_ij += abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)
        
    return 1/m * cost

def kmeans_fit_workplace(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_workplace(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_workplace(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1
     
def find_closest_centroids_specialities(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(32, 55):
                norm_ij += 2*abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_specialities(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(32, 55):
                norm_ij += abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)
        
    return 1/m * cost

def kmeans_fit_specialities(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_specialities(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_specialities(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1
     
     
def find_closest_centroids_field(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(0, 4):
                norm_ij += 3*abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_field(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(0, 4):
                norm_ij += 3*abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)
        
    return 1/m * cost

def kmeans_fit_field(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_field(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_field(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1


def find_closest_centroids_uni_work(X, centroids):
    K = centroids.shape[0]
    idx = np.zeros(X.shape[0], dtype=int)

    for i in range(X.shape[0]):
        distance = list()
        for j in range(K):
            norm_ij = np.linalg.norm(X[i] - centroids[j])
            for k in range(4, 32):
                norm_ij += 3*abs(X[i][k] - centroids[j][k])

            distance.append(norm_ij)
        idx[i] = np.argmin(distance)
    
    return idx

def cost_function_uni_work(X, centroids, idx):
    m = X.shape[0]
    cost = 0
    for i in range(m):
        norm_ij = np.linalg.norm(X[i] - centroids[idx[i]])
        for k in range(4, 32):
                norm_ij += abs(X[i][k] - centroids[idx[i]][k])
        cost += pow(norm_ij, 2)
        
    return 1/m * cost

def kmeans_fit_uni_work(X, K):
    m, n = X.shape
    centroids = kMeans_init_centroids(X, K)
    previous_centroids = centroids    
    idx = np.zeros(m)
    cost = 0
    
    i = 1
    while True:
        
        idx = find_closest_centroids_uni_work(X, centroids)
        previous_centroids = centroids
        centroids = compute_centroids(X, idx, K)
        new_cost = cost_function_uni_work(X, centroids, idx)
        print(new_cost)
        if new_cost == cost:
            return centroids, idx, cost
        else:
            cost = new_cost

        i+=1
