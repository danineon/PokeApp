##### ----> [Manually] Begin manual specific rules <---- #####
# Keep the model of classes ending in DTO (Data transfer objects)
-keep class **.*DTO { *; }
##### ----> [Manually] End manual specific rules <---- #####

##### ----> [Retrofit] Begin Retrofit specific rules <---- #####
# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response
 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
 ##### ----> [Retrofit] End Retrofit specific rules <---- #####