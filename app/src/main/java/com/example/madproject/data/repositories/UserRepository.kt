package com.example.madproject.data.repositories

import com.example.madproject.data.sources.ContactRemoteDataSource
import com.example.madproject.data.sources.UserRemoteDataSource

class UserRepository (
    private val userRemoteDataSource: UserRemoteDataSource,
    private val contactRemoteDataSource: ContactRemoteDataSource,
)