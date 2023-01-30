<style scoped>
	div.container img{
		max-width: 200px;
		max-height: 200px;
		display: block;
		margin: auto;
		margin-top: 20px;
		margin-bottom: 20px;
	}
</style>

<template>
	<div class="container">
		<h2>Déposer une image</h2>
		<hr/>
		<div class="large-12 medium-12 small-12 cell">
			<label>File Preview
				<input type="file" id="file" accept="image/*" @change="handleFileUpload( $event )"/>
			</label>
			<img v-bind:src="imagePreview" v-show="showPreview"/>
			<button v-on:click="submitFile()">Submit</button>
		</div>
	</div>
</template>

<script>
import {logimage} from './ http-api';
	import axios from 'axios';
	
	export default {
		data(){
			return {
				file: '',
				showPreview: false,
				imagePreview: ''
			}
		},
		methods: {
			
			submitFile(){
				
				let formData = new FormData();
				
				formData.append('file', this.file);
				
				axios.post( '/images',
					formData,
					{
						headers: {
								'Content-Type': 'multipart/form-data'
						}
					}
				).then(function(){
					logimage();
					console.log('SUCCESS!!');
				})
				.catch(function(){
					console.log('FAILURE!!');
				});
			},
			
			handleFileUpload( event ){
				
				this.file = event.target.files[0];
				
				let reader  = new FileReader();
				reader.addEventListener("load", function () {
					this.showPreview = true;
					this.imagePreview = reader.result;
				}.bind(this), false);
				
				if( this.file ){
					
					if ( /\.(jpe?g|png|gif)$/i.test( this.file.name ) ) {
						/*
							Fire the readAsDataURL method which will read the file in and
							upon completion fire a 'load' event which we will listen to and
							display the image in the preview.
						*/
						reader.readAsDataURL( this.file );
					}
				}
			}
		}
	}
</script>