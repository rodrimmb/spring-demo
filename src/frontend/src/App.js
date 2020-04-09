import React, { useState, useEffect, useCallback } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useDropzone} from 'react-dropzone';

const UserProfile = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile")
    .then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  }

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        {userProfile.id ? <img src={`http://localhost:8080/api/v1/user-profile/${userProfile.id}/image/download`}/> : <p>No hay imagen</p> }
        <br/>
        <br/>
        <h1>{userProfile.username}</h1>
        <p>{userProfile.id}</p>
        <Dropzone id = {userProfile.id}/>
        <br/>
      </div>
    );
  });
};

function Dropzone({ id }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    
    console.log(file);
    
    const formData = new FormData();
    formData.append("file", file);
    
    axios.post(
      `http://localhost:8080/api/v1/user-profile/${id}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/from-data"
        }
      }
    ).then( () => {
      console.log("Fichero subido correctamente");
    }
    ).catch( err => {
      console.log(err);
    });

  }, []);
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the profile image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfile />
    </div>
  );
}

export default App;
