import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler


def estimate_gaussian(X):
    m, n = X.shape

    mu = np.mean(X, axis=0)
    var = np.var(X, axis=0)

    return mu, var


def ProbabilityAnomaly(X):
    mu, var = estimate_gaussian(X)

    P_X = dict()
    idx = 0
    for user in X:
        p_x_i = (1 / np.sqrt(2 * np.pi * var)) * np.exp(-(np.power(user - mu, 2)) / (2 * var))
        P_X[idx] = np.prod(p_x_i)
        idx += 1

    return P_X


def getDataAnomalyDetection():
    df = pd.read_json("/media/influx/Programming/Projects/project-final-random/Recommender/users.json")

    df_train = df.drop(['dateOfBirth', 'name', 'email', 'profile_pic', 'connectionId'], axis=1)

    df_train = pd.get_dummies(df_train, columns=["field"])
    df_train = pd.get_dummies(df_train, columns=["universityLocation"])
    df_train = pd.get_dummies(df_train, columns=["workplace"])

    spec = set(s for user in df_train['specialties'] for s in user)
    for special in spec:
        df_train[special] = [0 for i in range(2003)]

    i = 0
    for user in df_train['specialties']:
        for s in user:
            df_train.at[i, s] = 1
        i += 1

    df_train = df_train.drop(['specialties', 'id'], axis=1)

    ability_power = list()
    for user in df_train.values:
        if (user[0] == 1):
            power = 4
        elif user[1] == 1:
            power = 3
        elif user[2] == 1:
            power = 2
        elif user[3] == 1:
            power = 1

        ability = 0
        for i in range(33, 55):
            if (user[i] == 1):
                ability += 1

        ability_power.append(ability / np.power(power, 2) + 1.373 * np.power(power, 2) / ability)

    # In[12]:

    df_train['ability_power'] = ability_power

    # In[13]:

    df_train = df_train.drop(['field_AA', 'field_BB', 'field_CC', 'field_DD', 'Algorithm', 'Database', 'Game'], axis=1)

    df_train = df_train.drop(['Python', 'C#', 'Software', 'Back-End', 'Fast Type', 'Verilog'], axis=1)

    df_train = df_train.drop(['Photoshop', 'AI', 'Java', 'PHP', 'Data', 'Front-End', 'Hardware', 'Django'], axis=1)

    df_train = df_train.drop(['C++', 'Crypto', 'Full-Stack', 'IT', 'Machine Learning', 'Blockchain'], axis=1)

    dsc = df_train['ability_power'].values
    sc = StandardScaler()
    dsc = dsc.reshape(-1, 1)
    dsc = sc.fit_transform(dsc)

    df_train["ability_power"] = dsc

    X = df_train.values

    x = ProbabilityAnomaly(X)

    x = dict(sorted(x.items(), key=lambda item: item[1]))

    epsilon = np.power(0.1, 20)

    anomaly_users = {(user, x[user]) for user in x if x[user] < epsilon}

    return anomaly_users
