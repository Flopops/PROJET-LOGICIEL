
import axios, { AxiosResponse} from 'axios';
import { getTransitionRawChildren, ref } from 'vue'

export var imageUrl = "/images/";

export const selectid = ref("") ;

export const ListImage = ref([{"name": "", "id": 0}]);



export function getImage(){                // requete
    axios.get( imageUrl + selectid.value , { responseType:"blob" })
        .then(function (response: AxiosResponse) {
      const reader = new window.FileReader();
      reader.readAsDataURL(response.data); 
      reader.onload = function() {		  			
      const imageEl =  document.querySelector("#img");
          const imageDataUrl = (reader.result as string);
          if(imageEl)
          imageEl.setAttribute("src", imageDataUrl);
      }
    });
  }





export function logimage(){
  axios.get('/images')
.then(function (response: AxiosResponse) {
  ListImage.value = (response.data);
  console.log(ListImage.value);
})
.catch(function (error) {
  console.log(error);
});
}



logimage();




	






