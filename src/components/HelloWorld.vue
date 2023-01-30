<script setup lang="ts">


import { ListImage,selectid, getImage} from './ http-api';
</script>



<template>

<h2>Selectionner votre image</h2>
  <li>                  <!-- j'utilise change pour appeller la fonction getimage-->
    <select v-model="selectid"  @change="getImage" >
      <option value="">Select here</option>     
      <option v-for="images in ListImage" :value= "images.id" > {{ images.name }} </option>
  </select>
  </li>
  
  <img id="img" >   <!-- j'appelle la source de l'image qui a changé -->


</template>





<style scoped>

img {
  height: 15em;
  padding: 1em;

}
.read-the-docs {
  color: #888;
}
</style>
