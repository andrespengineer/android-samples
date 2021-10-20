package com.social.presentation.controls

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import java.lang.reflect.InvocationTargetException

class CachedFragmentFactory constructor(var fragmentSet: Set<String?> = setOf()) : FragmentFactory() {

    val cachedFragments = HashMap<String, Fragment?>()

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        try {

            // If fragments exists in fragmentSet (fragments to retain instances) and is already Cached
           if(fragmentSet.contains(className) && cachedFragments.containsKey(className))
                  return cachedFragments[className]!!
           else
           {
                val cls = loadFragmentClass(classLoader, className)
                val fragment = cls.getConstructor().newInstance()!!

                if(fragmentSet.contains(className))
                    cachedFragments[className] = fragment

               return fragment
            }

        } catch (e: InstantiationException) {
            throw Fragment.InstantiationException(
                "Unable to instantiate fragment " + className
                        + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public", e
            )
        } catch (e: IllegalAccessException) {
            throw Fragment.InstantiationException(
                ("Unable to instantiate fragment " + className
                        + ": make sure class name exists, is public, and has an"
                        + " empty constructor that is public"), e
            )
        } catch (e: NoSuchMethodException) {
            throw Fragment.InstantiationException(
                ("Unable to instantiate fragment " + className
                        + ": could not find Fragment constructor"), e
            )
        } catch (e: InvocationTargetException) {
            throw Fragment.InstantiationException(
                ("Unable to instantiate fragment " + className
                        + ": calling Fragment constructor caused an exception"), e
            )
        }
    }

    fun removeEntriesFromCache(key: String) {
        if(key in cachedFragments) {
            cachedFragments[key]?.onDestroy()
            cachedFragments[key] = null
            cachedFragments.remove(key)
        }
    }

}