<script setup lang="ts">
import {imageUrl, ListImage} from './ http-api';

</script>





<template>
    <h2>Galerie d'Image</h2>
    <img v-for="image in ListImage" :src= "imageUrl + image.id "/>   
</template>

<style scoped>
img {
  height: 15em;
  padding: 1em;
}
</style>