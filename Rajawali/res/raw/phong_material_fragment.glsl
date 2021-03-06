precision mediump float;
precision mediump int;

varying vec2 vTextureCoord;
varying vec3 vNormal;
varying vec3 vEyeVec;
varying vec4 vColor;

%FOG_FRAGMENT_VARS%
%LIGHT_VARS%

uniform float uShininess;
uniform vec4 uSpecularColor;
uniform vec4 uAmbientColor;
uniform vec4 uAmbientIntensity; 
uniform sampler2D uDiffuseTexture;
uniform sampler2D uNormalTexture;
uniform sampler2D uSpecularTexture;
uniform sampler2D uAlphaTexture;

void main() {
   vec4 Kd = vec4(0.0);
   float intensity = 0.0;
   float Ks = 0.0;
   float NdotL = 0.0;
    float power = 0.0;
   vec3 N = normalize(vNormal);
   vec3 L = vec3(0.0);

#ifdef NORMAL_MAP
   vec3 normalmap = normalize(texture2D(uNormalTexture, vTextureCoord).rgb * 2.0 - 1.0);
   normalmap.z = -normalmap.z;
   N = normalize(N + normalmap);
#endif

%LIGHT_CODE%

#ifdef TEXTURED
   vec4 diffuse = Kd * texture2D(uDiffuseTexture, vTextureCoord);
#else
   vec4 diffuse = Kd * vColor;
#endif

#ifdef SPECULAR_MAP
   vec4 specular = Ks * uSpecularColor * texture2D(uSpecularTexture, vTextureCoord);
#else
   vec4 specular = Ks * uSpecularColor; 
#endif

   vec4 ambient = uAmbientIntensity * uAmbientColor; 
   gl_FragColor = ambient + diffuse + specular;    

#ifdef ALPHA_MAP
   float alpha = texture2D(uAlphaTexture, vTextureCoord).r;
   gl_FragColor.a = alpha; 
#endif

M_FOG_FRAGMENT_COLOR
}